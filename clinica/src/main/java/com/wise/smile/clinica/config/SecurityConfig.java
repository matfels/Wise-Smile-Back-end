package com.wise.smile.clinica.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration

public class SecurityConfig {
	
	@Autowired
    private SecurityFilter securityFilter;
	
	// Nao pede a senha ao iniciar o spring
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                // Avisa ao Spring que a nossa autenticação será via Token (Stateless)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    // Libera APENAS a rota de login (para a pessoa conseguir o crachá)
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    // BLOQUEIA todo o resto! Qualquer outra rota exige estar autenticado.
                    req.anyRequest().authenticated();
                })
                // Coloca o nosso Filtro (Catraca) ANTES do filtro padrão do Spring
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    

    
    // @Bean diz ao Spring para deixar este encriptador pronto (injetado) noutras partes do código
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}