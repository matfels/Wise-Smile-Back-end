package com.wise.smile.clinica.controller;

import com.wise.smile.clinica.entity.Dentista;
import com.wise.smile.clinica.service.DentistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
}