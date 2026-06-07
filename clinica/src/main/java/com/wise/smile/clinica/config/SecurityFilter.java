package com.wise.smile.clinica.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wise.smile.clinica.repositories.UsuarioRepositories;
import com.wise.smile.clinica.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepositories repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
    		throws ServletException, IOException  {
        // Pega o Token do cabeçalho da requisição
        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null)  {
            // valida o Token e descobre quem e o dono (por email)
            var subject = tokenService.getSubject(tokenJWT);
            
            // Busca o usuario no banco de dados
            var usuario = repository.findByEmail(subject).get();
            
            // Força a autenticaçao no Spring para a requisição
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Libera a requisição para continuar o fluxo (mandar para o Controller)
        filterChain.doFilter(request, response );
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ",""); // Remove o prefixo padro da web
        }
        
        return null;
    }
}