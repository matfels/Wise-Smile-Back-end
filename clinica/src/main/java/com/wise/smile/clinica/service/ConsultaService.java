package com.wise.smile.clinica.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wise.smile.clinica.entity.Consulta;
import com.wise.smile.clinica.entity.StatusConsulta;
import com.wise.smile.clinica.repositories.ConsultaRepositories;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepositories consultaRepository;

    // Marcar uma nova consulta
    public Consulta agendarConsulta(Consulta consulta) {
        
        //Não permitir agendamento em datas passadas
        if (consulta.getDataInicio().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Erro: Não é possível agendar consultas em datas passadas.");
        }

        //O horário final da consulta deve ser após o horário inicial
        if (consulta.getDataEnding().isBefore(consulta.getDataInicio()) || consulta.getDataEnding().isEqual(consulta.getDataInicio())) {
            throw new IllegalArgumentException ("Erro: O horário de término deve ser posterior ao horário de início.");
        }

        //Não permitir conflito de horário para odentista
        // Utilizamos método e criamos no ConsultaRepositories
        List<Consulta> conflitos = consultaRepository.findConflitosHorario(
                consulta.getDentista().getId(),
                consulta.getDataInicio(),
                consulta.getDataEnding()
        );

        if (!conflitos.isEmpty()){
            throw new IllegalArgumentException("Erro: O dentista selecionado já possui uma consulta marcada para este horário.");
        }

        // Passando emaem todas as validações, definimos o status inicial e guardamos
        consulta.setStatus(StatusConsulta.AGENDADA);
        return consultaRepository.save(consulta);
    }
    // Método para cancelar uma consulta
    public Consulta cancelarConsulta (Integer idConsulta, String motivoCancelamento){
        
        // Vai buscar a consulta à base de dados ou lança erro se não existir
        Consulta consulta = consultaRepository.findById(idConsulta)
                .orElseThrow(() -> new IllegalArgumentException("Erro: Consulta não encontrada."));

        // Regra 3: Para cancelar consultas é necessário um motivo
        if (motivoCancelamento == null || motivoCancelamento.trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: O motivo do cancelamento é obrigatório.") ;
        }

        // Atualiza os dados e guarda na base de dados
        consulta.setStatus(StatusConsulta.CANCELADA);
        consulta.setMotivoCancelamento(motivoCancelamento);
        
        return consultaRepository.save(consulta);
    }
}
