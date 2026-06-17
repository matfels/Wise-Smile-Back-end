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

import com.wise.smile.clinica.entity.Especialidade;
import com.wise.smile.clinica.service.EspecialidadeService;

@RestController
@RequestMapping("/especialidades" ) //Rota://localhost:8081/especialidades
public class EspecialidadeController {
    @Autowired
    private EspecialidadeService especialidadeService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Especialidade> salvar(@RequestBody Especialidade especialidade){
        Especialidade salva = especialidadeService.salvar(especialidade) ;
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping
    public ResponseEntity<List<Especialidade>> listarTodas(){
        return ResponseEntity.ok(especialidadeService.listarTodas() );
    }
    
    


    @GetMapping("/{id}")
    public ResponseEntity<Especialidade> buscarPorId(@PathVariable Integer id) {
        Optional<Especialidade> especialidade = especialidadeService.buscarPorId(id);
        return especialidade.map(ResponseEntity::ok)
                            .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<Especialidade> atualizar(@RequestBody Especialidade especialidade) {
        Especialidade atualizada = especialidadeService.atualizar(especialidade);
        return ResponseEntity.ok(atualizada);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        especialidadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Integer id) {
        especialidadeService.ativarEspecialidade(id);
        return ResponseEntity.noContent().build();
    }
}