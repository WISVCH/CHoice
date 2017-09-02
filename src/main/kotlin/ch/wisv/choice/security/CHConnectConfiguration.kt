package ch.wisv.choice.security

import com.google.common.collect.ImmutableSet
import com.nimbusds.jose.JWSAlgorithm
import org.mitre.oauth2.model.ClientDetailsEntity
import org.mitre.oauth2.model.RegisteredClient
import org.mitre.openid.connect.client.service.ClientConfigurationService
import org.mitre.openid.connect.client.service.RegisteredClientService
import org.mitre.openid.connect.client.service.impl.DynamicRegistrationClientConfigurationService
import org.mitre.openid.connect.client.service.impl.JsonFileRegisteredClientService
import org.mitre.openid.connect.client.service.impl.StaticClientConfigurationService
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated

import javax.validation.constraints.NotNull
import java.util.Collections

import org.mitre.openid.connect.client.OIDCAuthenticationFilter.FILTER_PROCESSES_URL

@Component
@ConfigurationProperties(prefix = "wisvch.connect")
@Validated
@Profile("!test")
class CHConnectConfiguration {

    /**
     * OIDC Issuer URI; see $issuerUri/.well-known/openid-configuration
     */
    @NotNull
    var issuerUri: String? = null

    /**
     * URI of this application, without trailing slash
     */
    @NotNull
    var clientUri: String? = null

    /**
     * Groups that are admin in the system
     */
    var adminGroups: List<String>? = null

    private var registeredClient: RegisteredClient? = null

    init {
        registeredClient = RegisteredClient()
        registeredClient!!.scope = ImmutableSet.of("openid", "email", "profile", "ldap")
        registeredClient!!.tokenEndpointAuthMethod = ClientDetailsEntity.AuthMethod.SECRET_BASIC
        registeredClient!!.grantTypes = ImmutableSet.of("authorization_code")
        registeredClient!!.responseTypes = setOf("code")
        registeredClient!!.requestObjectSigningAlg = JWSAlgorithm.RS256
    }

    @Bean
    fun getRegisteredClient(): RegisteredClient {
        if (clientUri != null && registeredClient!!.redirectUris.isEmpty()) {
            registeredClient!!.redirectUris.add(clientUri!! + FILTER_PROCESSES_URL)
        }
        if (issuerUri != null && registeredClient!!.jwksUri == null) {
            registeredClient!!.jwksUri = issuerUri!! + "/jwk"
        }

        return registeredClient!!
    }

    fun setRegisteredClient(registeredClient: RegisteredClient) {
        this.registeredClient = registeredClient
    }

    /**
     * Dynamic client configuration service: this application is dynamically registered as an OIDC client when
     * authentication first occurs. This registration is persisted in a JSON file to avoid re-registration every time
     * the application is restarted.
     *
     * @return ClientConfigurationService
     */
    @Bean
    @Profile("!production")
    fun clientConfigurationService(): ClientConfigurationService {
        val registeredClientService = JsonFileRegisteredClientService("config/oidc-client-registration.json")

        val clientConfigurationService = DynamicRegistrationClientConfigurationService()
        clientConfigurationService.registeredClientService = registeredClientService
        clientConfigurationService.template = getRegisteredClient()
        return clientConfigurationService
    }


    /**
     * Static client configuration: in production, we use a client configured from Spring properties.
     *
     * @return ClientConfigurationService with a single statically configured client
     */
    @Bean
    @Profile("production")
    fun clientProdConfigurationService(): ClientConfigurationService {
        val clientConfigurationService = StaticClientConfigurationService()
        clientConfigurationService.clients = Collections.singletonMap(issuerUri, getRegisteredClient())

        return clientConfigurationService
    }
}