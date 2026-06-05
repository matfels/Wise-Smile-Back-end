package com.wise.smile.clinica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration

public class SecurityConfig {
	// Nao pede a senha ao iniciar o spring
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desliga a proteção CSRF (necessário para o Thunder Client funcionar)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Libera temporariamente o acesso a todas as rotas sem pedir senha
            );
        return http.build();
    }
    // @Bean diz ao Spring para deixar este encriptador pronto (injetado) noutras partes do código
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}