package com.wise.smile.clinica.service;

import com.wise.smile.clinica.entity.Usuario;
import com.wise.smile.clinica.repositories.UsuarioRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositories usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registarUsuario(Usuario usuario) {
        
        // Regra 1: Verificar se o e-mail já existe
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Erro: Este e-mail já está em uso por outro utilizador.");
        }

        // Regra 2: Verificar se o CPF
        if (usuarioRepository.findByCpf(usuario.getCpf()).isPresent()) {
            throw new IllegalArgumentException("Erro: Este CPF já se encontra registado no sistema.");
        }

        // Regra 3: Criptografa a palavra-passe antes de guardar
        // O BCrypt transforma "admin123" string ilegível
        String palavraPasseEncriptada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(palavraPasseEncriptada);

        // Se passar em todas as regras, guarda o utilizador
        return usuarioRepository.save(usuario);
    }
    
    // Método auxiliar para buscar um utilizador por ID
    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }
}