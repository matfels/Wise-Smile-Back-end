package com.wise.smile.clinica.service;

import com.wise.smile.clinica.repositories.UsuarioRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepositories repository;

    // Este é o método obrigatório que o "Gerente" (AuthenticationManager) vai chamar sozinho
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Vai à sua base de dados procurar o utilizador pelo e-mail
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilizador não encontrado no sistema!"));
    }
}