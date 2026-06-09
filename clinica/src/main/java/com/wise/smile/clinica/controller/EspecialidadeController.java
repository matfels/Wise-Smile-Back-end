package com.wise.smile.clinica.controller;

import com.wise.smile.clinica.entity.Especialidade;
import com.wise.smile.clinica.service.EspecialidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/especialidades" ) //Rota://localhost:8081/especialidades
public class EspecialidadeController {
    @Autowired
    private EspecialidadeService especialidadeService;

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

    @PutMapping
    public ResponseEntity<Especialidade> atualizar(@RequestBody Especialidade especialidade) {
        Especialidade atualizada = especialidadeService.atualizar(especialidade);
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        especialidadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}