package com.wise.smile.clinica.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration // Indica que esta classe contém configurações que o Spring deve ler ao iniciar
@EnableMethodSecurity // Permite o uso de anotações como @PreAuthorize nos controllers para controle de acesso por cargos
public class SecurityConfig {
	
	@Autowired // Injeta o nosso filtro de segurança (que intercepta as requisições para checar o JWT)
    private SecurityFilter securityFilter;
	
	@Bean // Exporta este método para o Spring usar como configuração central de segurança
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
        		.cors(cors -> cors.configurationSource(corsConfigurationSource())) // Aplica as regras de CORS (libera o front-end para acessar a API)
        		.csrf(csrf -> csrf.disable()) // Desabilita proteção CSRF (não é necessária quando usamos tokens JWT)
                // Avisa ao Spring que a autenticação será via Token (Stateless, ou seja, sem guardar sessão no servidor)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    // Libera APENAS a rota de login (para a pessoa conseguir o crachá)
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll();
                
                    
                    // NOVA REGRA ADICIONADA AQUI: Apenas ADMIN mexe nos usuários
                    req.requestMatchers("/usuarios/**").hasRole("ADMIN");
                    
                    // bloqueia todo o resto, todas as rotas exigem no mínimo estar logado.
                    req.anyRequest().authenticated();
                
                })
                // Coloca o nosso Filtro de checagem do JWT ANTES do filtro padrão do Spring Security
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

	// BLOCO ADICIONADO: Configuração para liberar a porta do Angular
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Libera acesso vindo do Angular na porta 4200
        config.setAllowedMethods(Arrays.asList("*")); // Permite todos os métodos HTTP (GET, POST, PUT, DELETE)
        config.setAllowedHeaders(Arrays.asList("*")); // Permite o envio de qualquer cabeçalho (como o de Authorization)
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Aplica essas regras do CORS para todas as rotas (/**)
        return source;
  
    }

    @Bean // Configura qual codificador de senhas a aplicação vai usar
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder(); // Retorna o BCrypt, que é seguro e cria hashes unidirecionais
    }
}
