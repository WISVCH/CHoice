package ch.wisv.choice.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.security.oauth2.core.oidc.user.OidcUser

/**
 * CH Connect Security Configuration
 */
@Configuration
@EnableWebSecurity
@ConfigurationProperties("wisvch.connect")
@Profile("!test")
class CHConnectSecurityConfiguration : WebSecurityConfigurerAdapter() {
    private var adminGroups: MutableSet<String?>? = null
    fun setAdminGroups(adminGroups: MutableSet<String?>?) {
        this.adminGroups = adminGroups
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity?) {
        http
                ?.authorizeRequests()
                ?.antMatchers("/dashboard/**")?.hasRole("ADMIN")
                ?.anyRequest()?.permitAll()
                ?.and()
                ?.oauth2Login()?.userInfoEndpoint()?.oidcUserService(oidcUserService())
    }

    private fun oidcUserService(): OAuth2UserService<OidcUserRequest?, OidcUser?>? {
        return OAuth2UserService<OidcUserRequest?, OidcUser?> { userRequest ->
            val ROLE_ADMIN = SimpleGrantedAuthority("ROLE_ADMIN")
            val ROLE_USER = SimpleGrantedAuthority("ROLE_USER")
            val idToken: OidcIdToken = userRequest!!.getIdToken()
            val groups = idToken.claims["google_groups"] as MutableCollection<String>
            val authorities: MutableList<SimpleGrantedAuthority?> = if (groups.stream().anyMatch { o: String? -> adminGroups!!.contains(o) }) mutableListOf(ROLE_ADMIN, ROLE_USER) else mutableListOf(ROLE_USER)
            DefaultOidcUser(authorities, idToken)
        }
    }
}
