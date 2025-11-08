package org.oladushek.module25.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll()  // Разрешаем все запросы без авторизации
                )
                .csrf(csrf -> csrf.disable())  // Отключаем CSRF
                .cors(cors -> cors.disable()); // Отключаем CORS (опционально)

        return http.build();
    }
}
