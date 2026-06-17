package com.wise.smile.clinica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wise.smile.clinica.entity.Consulta;
import com.wise.smile.clinica.entity.Usuario;
import com.wise.smile.clinica.service.ConsultaService;

@RestController
@RequestMapping("/consultas") //Rota: http://localhost:8081/consultas
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    // Rota POST para agendar a consulta
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")	
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
    public ResponseEntity<java.util.List<Consulta>> listarTodas(@AuthenticationPrincipal Usuario usuarioLogado) {
        return ResponseEntity.ok(consultaService.listarTodas(usuarioLogado));
    }

    // Rota GET: Busca uma consulta específica
    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarPorId(@PathVariable Integer id) {
        java.util.Optional<Consulta> consulta = consultaService.buscarPorId(id);
        return consulta.map(ResponseEntity::ok )
                       .orElseGet(()  -> ResponseEntity.notFound().build());
    }

    // PUT para ATUALIZAR a consulta 
    @PreAuthorize("hasRole('ADMIN')")
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