package org.ipr.childhub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Slf4j
@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class ResourceServerConfig {


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        log.info("Security Web Filter Chain");
        http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/swagger-ui/**", "/webjars/**", "/v3/**")
                        .permitAll())
                        //.anyExchange().authenticated())
                //.formLogin(formLoginSpec -> formLoginSpec.loginPage("http://localhost:7777/login"))
                .oauth2ResourceServer(oauth2 -> oauth2
                        .opaqueToken(opaque -> opaque
                                .introspectionUri("http://localhost:7777/oauth2/token-info")
                                .introspectionClientCredentials("test-client", "test-client")
                        )
                );
        return http.build();
    }
}

/*    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // выключаем поддержку сессий
        http.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(customizer -> {
                    customizer.anyRequest().authenticated();
                });

        // подключаем поддержку OAuth2 Resource Server с Opaque Token
        http.oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer ->
                httpSecurityOAuth2ResourceServerConfigurer.opaqueToken(withDefaults()));
        return http.build();
    }
}*/

