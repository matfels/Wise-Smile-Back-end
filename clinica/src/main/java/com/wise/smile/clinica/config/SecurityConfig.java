package com.wise.smile.clinica.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wise.smile.clinica.entity.Usuario;
import com.wise.smile.clinica.repositories.UsuarioRepositories;

@Service
public class SecurityConfig {

    @Autowired
    private UsuarioRepositories usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registarUsuario(Usuario usuario) {
        
        // Regra 1: Verificar se o e-mail ja existe
        if ( usuarioRepository.findByEmail(usuario.getEmail()).isPresent() ) {
            throw new IllegalArgumentException("Erro: Este e-mail já está em uso por outro utilizador.");
        }

        // Regra 2 Verificar se o CPF ja existe
        if (usuarioRepository.findByCpf(usuario.getCpf()).isPresent() ) {
            throw new IllegalArgumentException("Erro: Este CPF já se encontra registado no sistema.");
        }

        // Regra 3: criptografa a senha
        // O BCrypt transforma "admin123" string em uma criptografada string
        String palavraPasseEncriptada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(palavraPasseEncriptada);

        // Se passou em todas as regras, guarda o utilizador
        return usuarioRepository.save(usuario) ;
    }
    
    // metodo auxiliar para buscar um utilizador por ID
    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }
}