package com.wise.smile.clinica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wise.smile.clinica.entity.Usuario;
import com.wise.smile.clinica.repositories.UsuarioRepositories;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositories usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registarUsuario(Usuario usuario) {
        
        // Verifica se o e-mail já existe
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent())  {
            throw new IllegalArgumentException("Erro: Este e-mail já está em uso por outro utilizador.");
        }

        
        // Verificaa se o CPF existe
        if (usuarioRepository.findByCpf(usuario.getCpf()).isPresent()) {
            throw new IllegalArgumentException("Erro: Este CPF já se encontra registado no sistema.");
        }

        // Criptografa a senha antes de guardar
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
    
    //listar todos os usuários
    public List<Usuario> listarTodos() {
        return  .findAll();
    }

    //  atualizar um usuário
    public Usuario atualizarUsuario(Usuario usuario)  {
        // Atenção de Segurança: Se for atualizar a senha nesta rota, 
        // ela precisaria passar pelo passwordEncoder.encode() novamente.
        return usuarioRepository.save(usuario);
    }

    //Inativar um usuário (Exclusão Lógica)
    public void deletarUsuario(Integer id) {
        var usuario = usuarioRepository.findById(id).get();
        usuario.setAtivo(false); // Quando ativo for false, o Spring Security bloqueia o login dele!
        usuarioRepository.save(usuario);
    }
    
 // Método para reativar o usuário no banco de dados
    public void ativarUsuario(Integer id) {
        // 1. Busca o usuário pelo ID
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        
        // 2. Muda o status dele para TRUE (Ativo)
        usuario.setAtivo(true);
        
        // 3. Salva a alteração no banco
        usuarioRepository.save(usuario);
    }

}