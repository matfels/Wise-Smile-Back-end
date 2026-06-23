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

import com.wise.smile.clinica.entity.Paciente;
import com.wise.smile.clinica.service.PacienteService;

// dizem ao Spring que esta classe é um Roteador Web / API REST
@RestController
@RequestMapping("/pacientes") //as rotas aqui vão começar com http://localhost:8081/pacientes
public class PacienteController  {

    @Autowired
    private PacienteService pacienteService;
    
    // Rota Usada para CRIAR um novo paciente
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> registarPaciente( @RequestBody Paciente paciente) {
        try {
            Paciente pacienteSalvo = pacienteService.registarPaciente(paciente);
            // Devolve o código 201 (Criado - Criated) e os dados do paciente
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteSalvo) ;
        } catch (IllegalArgumentException e) {
            // quando o service +bloquear devolve o erro 400 (Bad Request)
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //GET lista todos os pacientes
    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos() {
        List<Paciente> pacientes = pacienteService.listarTodos();
        return ResponseEntity.ok(pacientes) ; // Devolve o código 200 (OK) e a lista
    }

    // Rota GET com ID: busca apenas um paciente específico
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Integer id) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(id);
        
        // quando encontrar, retorna 200  (OK). Se não, retorna 404 (Not Found)
        return paciente.map( ResponseEntity::ok)
                       .orElseGet( () -> ResponseEntity.notFound().build());
    }

    // Rota DELETE: remove um paciente
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPaciente(@PathVariable Integer id ) {
        pacienteService.deletarPaciente(id);
        return ResponseEntity.noContent().build(); // Devolve 204 (No Content) significando que foi apagado
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Integer id) {
        pacienteService.ativar(id);
        return ResponseEntity.noContent().build();
    }
    
    // Rota Put Atualiza um paciente 
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<Paciente> atualizarPaciente(@RequestBody Paciente paciente) {
        Paciente pacienteAtualizado = pacienteService.atualizarPaciente(paciente);
        return ResponseEntity.ok(pacienteAtualizado);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizar(@PathVariable Integer id, @RequestBody Paciente pacienteAtualizado) {
        // Garante que o ID do corpo da requisição é o mesmo da URL
        pacienteAtualizado.setId(id); 
        Paciente salvo = pacienteService.atualizarPaciente(pacienteAtualizado);
        return ResponseEntity.ok(salvo);
    }
    
}