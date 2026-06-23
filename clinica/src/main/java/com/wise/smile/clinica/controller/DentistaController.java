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

import com.wise.smile.clinica.entity.Dentista;
import com.wise.smile.clinica.service.DentistaService;

@RestController
@RequestMapping("/dentistas") // Rota http://localhost:8081/dentistas
public class DentistaController{

    @Autowired
    private DentistaService dentistaService;
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> registarDentista(@RequestBody Dentista dentista) {
        try {
            // Log para ver o que chegou no Java
            System.out.println("Dentista recebido: " + dentista);
            System.out.println("Especialidades: " + dentista.getEspecialidades());
            
            Dentista dentistaSalvo = dentistaService.registarDentista(dentista);
            return ResponseEntity.status(HttpStatus.CREATED).body(dentistaSalvo);
        } catch (Exception e) {
            // Mostra o erro real no terminal do Spring Boot
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body("Erro no servidor: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Dentista>> listarTodos(){
        return ResponseEntity.ok(dentistaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dentista> buscarPorId(@PathVariable Integer id){
        Optional<Dentista> dentista = dentistaService.buscarPorId(id);
        return dentista.map(ResponseEntity::ok)
                       .orElseGet(()-> ResponseEntity.notFound().build()) ;
    }
    
 // Rota Put Atualiza um dentista 
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<Dentista> atualizarDentista(@RequestBody Dentista dentista) {
        Dentista dentistaAtualizado =  dentistaService.atualizarDentista(dentista);
        return ResponseEntity.ok(dentistaAtualizado);
    }

    // Rota Delete : Remove um dentista (Exclusão Lógica)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDentista(@PathVariable Integer id )  {
        dentistaService.deletarDentista(id);
        return ResponseEntity.noContent().build() ; // Devolve 204 (No Content)
    }

 // Rota Put: Reativa um dentista (Volta o status para true)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/ativar")
    public ResponseEntity<Void> ativarDentista(@PathVariable Integer id) {
        dentistaService.ativarDentista(id);
        return ResponseEntity.noContent().build();
    }
   
    @GetMapping("/especialidade/{id}")
    public ResponseEntity<List<Dentista>> listarPorEspecialidade(@PathVariable Integer id) {
        List<Dentista> dentistasFiltrados = dentistaService.buscarPorEspecialidadeId(id);
        return ResponseEntity.ok(dentistasFiltrados);
    }
    
    // Rota PUT para Atualizar
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Dentista> atualizar(@PathVariable Integer id, @RequestBody Dentista dentistaAtualizado) {
        dentistaAtualizado.setId(id);
        // Ajuste "salvar" ou "atualizarDentista" conforme estiver no seu DentistaService.java
        Dentista salvo = dentistaService.atualizarDentista(dentistaAtualizado);
        return ResponseEntity.ok(salvo);
    }
    
    
}