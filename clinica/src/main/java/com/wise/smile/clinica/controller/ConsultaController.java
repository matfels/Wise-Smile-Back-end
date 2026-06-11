package com.wise.smile.clinica.controller;

import com.wise.smile.clinica.entity.Consulta;
import com.wise.smile.clinica.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas") //Rota: http://localhost:8081/consultas
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    // Rota POST para agendar a consulta
    @PostMapping
    public ResponseEntity<?> agendarConsulta(@RequestBody Consulta consulta){
        try {
            Consulta consultaAgendada = consultaService.agendarConsulta(consulta) ;
            return ResponseEntity.status(HttpStatus.CREATED).body(consultaAgendada);
        } catch (IllegalArgumentException e) {
            // Caso caia nas regras de negócio (data no passado, conflito de horário)
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Rota PUT para CANCELAR a consulta 
    // Ex: http://localhost:8081/consultas/1/cancelar?motivo=Paciente doente
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarConsulta(
            @PathVariable Integer id, 
            @RequestParam String motivo){
        try {
            Consulta consultaCancelada = consultaService.cancelarConsulta(id, motivo);
            return ResponseEntity.ok(consultaCancelada) ;
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    
    }
    
    
    // GET Lista todas as consultas
    @GetMapping
    public ResponseEntity<java.util.List<Consulta>> listarTodas() {
        return ResponseEntity.ok(consultaService.listarTodas() );
    }
    

    // Rota GET: Busca uma consulta específica
    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarPorId(@PathVariable Integer id) {
        java.util.Optional<Consulta> consulta = consultaService.buscarPorId(id);
        return consulta.map(ResponseEntity::ok )
                       .orElseGet(()  -> ResponseEntity.notFound().build());
    }

    // PUT para ATUALIZAR a consulta 
    
    @PutMapping
    public ResponseEntity<?> atualizarConsulta(@RequestBody Consulta consulta) {
        try {
            Consulta consultaAtualizada = consultaService.atualizarConsulta(consulta);
            return ResponseEntity.ok(consultaAtualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}