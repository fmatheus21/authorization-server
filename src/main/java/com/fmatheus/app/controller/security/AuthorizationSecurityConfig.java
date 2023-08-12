package com.fmatheus.app.controller.security;

import com.fmatheus.app.config.properties.JksProperties;
import com.fmatheus.app.config.properties.RegistredClientProperties;
import com.fmatheus.app.model.service.UserService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import java.io.InputStream;
import java.security.KeyStore;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class AuthorizationSecurityConfig {

    private final JksProperties jksProperties;

    private final RegistredClientProperties registredClientProperties;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.formLogin(Customizer.withDefaults()).build();
    }

    @Bean
    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
        return http
                //.csrf(AbstractHttpConfigurer::disable)
                //.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults()).build();
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtEncodingContextOAuth2TokenCustomizer(UserService userService, CustomUserDetailsService customUserDetailsService) {
        return (context -> {
            Authentication authentication = context.getPrincipal();
            if (authentication.getPrincipal() instanceof User user) {

                CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(user.getUsername());

                Set<String> authorities = new HashSet<>();
                for (GrantedAuthority authority : customUserDetails.getAuthorities()) {
                    authorities.add(authority.toString());
                }

                context.getClaims().claim("id", customUserDetails.getUser().getId().toString());
                context.getClaims().claim("fullname", customUserDetails.getUser().getPerson().getName());
                context.getClaims().claim("authorities", authorities);
            }

        });
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder, JdbcTemplate jdbcTemplate) {

        CharSequence clientCredentialsSecret = new StringBuilder(this.registredClientProperties.getClientSecretOne());
        RegisteredClient registeredClientCredentials = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId(this.registredClientProperties.getClientIdOne())
                .clientSecret(passwordEncoder.encode(clientCredentialsSecret))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope(OidcScopes.OPENID)
                .scope("read")
                .scope("write")
                .build();

        CharSequence authorizationCodeSecret = new StringBuilder(this.registredClientProperties.getClientSecretTwo());
        RegisteredClient registeredAuthorizationCode = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId(this.registredClientProperties.getClientIdTwo())
                .clientSecret(passwordEncoder.encode(authorizationCodeSecret))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://localhost:3000/authorized")
                .redirectUri("https://oidcdebugger.com/debug")
                .redirectUri("https://oauth.pstmn.io/v1/callback")
                .scope(OidcScopes.OPENID)
                .scope("read")
                .scope("write")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(15))
                        .refreshTokenTimeToLive(Duration.ofDays(1))
                        .reuseRefreshTokens(false)
                        .build())
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(false) // True - Requer a confirmacao dos escopos.
                        .requireProofKey(false) // True - Requer  uma chave para cada solicitacao de autorizacao.
                        .build())
                .build();

        return new InMemoryRegisteredClientRepository(registeredClientCredentials, registeredAuthorizationCode);


    }

    @Bean
    public OAuth2AuthorizationService auth2AuthorizationService(JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
    }

    @Bean
    public OAuth2AuthorizationConsentService oAuth2AuthorizationConsentService(JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcOperations, registeredClientRepository);
    }


    @SneakyThrows
    @Bean
    public JWKSet jwkSet() {
        final String jksPath = this.jksProperties.getPath();
        final InputStream inputStream = new ClassPathResource(jksPath).getInputStream();
        final KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(inputStream, jksProperties.getStorepass().toCharArray());
        RSAKey rsaKey = RSAKey.load(keyStore, jksProperties.getAlias(), jksProperties.getKeypass().toCharArray());
        return new JWKSet(rsaKey);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(JWKSet jwkSet) {
        return ((jwkSelector, securityContext) -> jwkSelector.select(jwkSet));
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }


}
