package com.bn.funcionario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // API REST stateless: CSRF desabilitado para permitir POST/PUT/DELETE via cliente HTTP
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // GET e POST liberados
                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/**").permitAll()
                // PUT e DELETE exigem autenticação
                .requestMatchers(HttpMethod.PUT, "/api/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/**").authenticated()
                // libera a página de erro padrão, senão erros em rotas públicas viram 401
                .requestMatchers("/error").permitAll()
                // qualquer outra requisição também exige autenticação
                .anyRequest().authenticated()
            )
            // autenticação HTTP Basic
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
