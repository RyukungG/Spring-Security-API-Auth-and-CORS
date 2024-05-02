package ku.book.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.oauth2.core.authorization.OAuth2AuthorizationManagers.hasScope;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Value("${auth0.audience}")
    private String audience;


    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/*"))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.GET).access(hasScope("read:books"))
                        .requestMatchers(HttpMethod.POST).access(hasScope("create:books"))
                        .anyRequest().authenticated())



                // use oauth as a resource server to do jwt validation
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth -> oauth
                        .jwt(jwt -> jwt.decoder(jwtDecoder())));


        return http.build();
    }


    private JwtDecoder jwtDecoder() {
        OAuth2TokenValidator<Jwt> withAudience =
                new AudienceValidator(audience);


        OAuth2TokenValidator<Jwt> withIssuer =
                JwtValidators.createDefaultWithIssuer(issuer);


        OAuth2TokenValidator<Jwt> validator =
                new DelegatingOAuth2TokenValidator<>(withAudience, withIssuer);


        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder)
                JwtDecoders.fromOidcIssuerLocation(issuer);


        jwtDecoder.setJwtValidator(validator);


        return jwtDecoder;
    }
}
