/*
 * Copyright (c) 2016  W.I.S.V. 'Christiaan Huygens'
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ch.wisv.choice.security

import ch.wisv.connect.client.CHUserInfoFetcher
import ch.wisv.connect.common.model.CHUserInfo
import com.google.common.collect.ImmutableSet
import org.mitre.openid.connect.client.OIDCAuthenticationFilter
import org.mitre.openid.connect.client.OIDCAuthenticationFilter.FILTER_PROCESSES_URL
import org.mitre.openid.connect.client.OIDCAuthenticationProvider
import org.mitre.openid.connect.client.service.ClientConfigurationService
import org.mitre.openid.connect.client.service.ServerConfigurationService
import org.mitre.openid.connect.client.service.impl.DynamicServerConfigurationService
import org.mitre.openid.connect.client.service.impl.PlainAuthRequestUrlBuilder
import org.mitre.openid.connect.client.service.impl.StaticSingleIssuerService
import org.mitre.openid.connect.web.UserInfoInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
//@Order(2147483640) // This value used to be defined as SecurityProperties.ACCESS_OVERRIDE_ORDER but has been removed as export
@Profile("!test")
class CHConnectSecurityConfiguration(private val properties: CHConnectConfiguration, private val clientConfigurationService: ClientConfigurationService) : WebSecurityConfigurerAdapter() {

    /**
     * Configure the [HttpSecurity] object.
     */
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http.cors().and()
                    .addFilterBefore(oidcAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter::class.java)
                    .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                .and()
                    .authorizeRequests()
                    .antMatchers("/dashboard/**").hasRole("ADMIN")
                    .anyRequest().permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
        // @formatter:on
    }

    /**
     * Configure OIDC authentication provider in manager.
     */
    @Autowired
    @Throws(Exception::class)
    fun configureAuthenticationProvider(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(oidcAuthenticationProvider())
    }

    /**
     * Configure the OIDC login path as our authentication entry point.
     *
     * @return AuthenticationEntryPoint
     */
    @Bean
    fun authenticationEntryPoint(): AuthenticationEntryPoint {
        return LoginUrlAuthenticationEntryPoint(FILTER_PROCESSES_URL)
    }

    /**
     * Register OIDC [UserInfoInterceptor], which makes UserInfo available in MVC views as a request attribute.
     *
     * @return WebMvcConfigurerAdapter
     */
    @Bean
    fun mvcInterceptor(): WebMvcConfigurerAdapter {
        return object : WebMvcConfigurerAdapter() {
            override fun addInterceptors(registry: InterceptorRegistry) {
                registry!!.addInterceptor(UserInfoInterceptor())
            }
        }
    }

    /**
     * OIDC authentication provider with authorities mapper which assigns authorities (roles) to users when they log in.
     *
     * @return AuthenticationProvider
     */
    @Bean
    fun oidcAuthenticationProvider(): AuthenticationProvider {
        // TODO: fully configurable roles
        val ROLE_ADMIN = SimpleGrantedAuthority("ROLE_ADMIN")
        val ROLE_USER = SimpleGrantedAuthority("ROLE_USER")

        val authenticationProvider = OIDCAuthenticationProvider()
        authenticationProvider.setUserInfoFetcher(CHUserInfoFetcher())

        authenticationProvider.setAuthoritiesMapper { _, userInfo ->
            if (userInfo is CHUserInfo) {
                if (properties.adminGroups!!.stream().anyMatch { userInfo.ldapGroups.contains(it) }) {
                    ImmutableSet.of(ROLE_ADMIN, ROLE_USER)
                } else {
                    ImmutableSet.of(ROLE_USER)
                }
            } else {
                ImmutableSet.of<SimpleGrantedAuthority>()
            }
        }

        return authenticationProvider
    }

    /**
     * OIDC authentication filter which does the actual authentication. The OIDC server and this client are
     * registered through the respective services. We support only one OIDC issuer, hence the
     * [StaticSingleIssuerService].
     *
     * @return OIDCAuthenticationFilter
     */
    @Bean
    @Throws(Exception::class)
    fun oidcAuthenticationFilter(): OIDCAuthenticationFilter {
        val oidcFilter = OIDCAuthenticationFilter()

        oidcFilter.setAuthenticationManager(authenticationManager())

        oidcFilter.serverConfigurationService = serverConfigurationService()
        oidcFilter.clientConfigurationService = clientConfigurationService

        val issuer = StaticSingleIssuerService()
        issuer.issuer = properties.issuerUri
        oidcFilter.issuerService = issuer

        // TODO: for production, sign or encrypt requests
        oidcFilter.authRequestUrlBuilder = PlainAuthRequestUrlBuilder()

        return oidcFilter
    }

    /**
     * Dynamic server configuration service: the information at $issuerUri/.well-known/openid-configuration is used
     * to configure the OIDC server.
     *
     * @return ServerConfigurationService
     */
    @Bean
    fun serverConfigurationService(): ServerConfigurationService {
        val serverConfigurationService = DynamicServerConfigurationService()
        serverConfigurationService.whitelist = setOf<String>(properties.issuerUri!!)
        return serverConfigurationService
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())

        return source
    }

}