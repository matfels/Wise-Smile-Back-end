package com.wise.smile.clinica.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    
    
    @PostMapping
    public ResponseEntity<?> registarDentista( @RequestBody Dentista dentista ){
        try {
            Dentista dentistaSalvo = dentistaService.registarDentista(dentista);
            return ResponseEntity.status(HttpStatus.CREATED).body(dentistaSalvo);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage() );
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
    @PutMapping
    public ResponseEntity<Dentista> atualizarDentista(@RequestBody Dentista dentista) {
        Dentista dentistaAtualizado =  dentistaService.atualizarDentista(dentista);
        return ResponseEntity.ok(dentistaAtualizado);
    }

    // Rota Delete : Remove um dentista (Exclusão Lógica)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDentista(@PathVariable Integer id )  {
        dentistaService.deletarDentista(id);
        return ResponseEntity.noContent().build() ; // Devolve 204 (No Content)
    }

 // Rota Put: Reativa um dentista (Volta o status para true)
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
    
    
    
}