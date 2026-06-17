package com.wise.smile.clinica.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wise.smile.clinica.entity.Usuario;
import com.wise.smile.clinica.service.UsuarioService;

@RestController
@RequestMapping("/usuarios") // http://localhost:8081/usuarios
public class UsuarioController {

	
    @Autowired
    private UsuarioService usuarioService;

    // POST Cria um novo usuário (criptografando a senha)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> registarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioSalvo = usuarioService.registarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // GET Lista os usuários 
    @GetMapping 
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    
    }
    
    // GET por ID: Busca um usuário por ID
    @GetMapping("/{id}" )
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        return usuario.map(ResponseEntity::ok)
                      .orElseGet( () -> ResponseEntity.notFound().build());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/ativar")
    public ResponseEntity<Void> ativarUsuario(@PathVariable Integer id) {
        usuarioService.ativarUsuario(id); // ou o nome que estiver no seu service
        return ResponseEntity.noContent().build();
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    //PUT: Atualiza um usuário 
    @PutMapping 
    public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    // DELETE Removendo usuario (Exclusão Lógica )
    @DeleteMapping("/{id}" )
    public ResponseEntity<Void> deletarUsuario(@PathVariable Integer id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build(); // Devolve 204 (No Content)
    }
    @PreAuthorize("hasRole('ADMIN')")
 // Rota PUT específica para a Edição pelo ID da URL
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Integer id, @RequestBody Usuario usuarioAtualizado) {
        
        // Garante que o ID do corpo da requisição é o mesmo da URL
        usuarioAtualizado.setId(id); 
        
        // Chama o método de atualização que já existe no seu Service
        Usuario salvo = usuarioService.atualizarUsuario(usuarioAtualizado);
        
        return ResponseEntity.ok(salvo);
    }

}