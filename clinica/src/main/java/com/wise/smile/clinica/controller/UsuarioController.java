package com.wise.smile.clinica.controller;

import com.wise.smile.clinica.entity.Usuario;
import com.wise.smile.clinica.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios") // http://localhost:8081/usuarios
public class UsuarioController {

	
    @Autowired
    private UsuarioService usuarioService;

    // POST Cria um novo usuário (criptografando a senha)
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
    

    //PUT: Atualiza um usuário 
    @PutMapping 
    public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    
    }

    // DELETE Removendo usuario (Exclusão Lógica )
    @DeleteMapping("/{id}" )
    public ResponseEntity<Void> deletarUsuario(@PathVariable Integer id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build(); // Devolve 204 (No Content)
    }

}