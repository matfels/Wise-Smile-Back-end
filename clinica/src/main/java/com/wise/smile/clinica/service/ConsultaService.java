package com.wise.smile.clinica.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wise.smile.clinica.entity.Consulta;
import com.wise.smile.clinica.entity.Dentista;
import com.wise.smile.clinica.entity.StatusConsulta;
import com.wise.smile.clinica.entity.Usuario;
import com.wise.smile.clinica.repositories.ConsultaRepositories;
import com.wise.smile.clinica.repositories.DentistaRepositories;
@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepositories consultaRepository;

    @Autowired
    private DentistaRepositories dentistaRepository;
    
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
        // Utilizamos metodo e criamos no ConsultaRepositories
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
    
    public List<Consulta> listarTodas(Usuario usuarioLogado) {
        
        // Se for ADMIN, traz absolutamente todas as consultas do sistema
        if ("ADMIN".equalsIgnoreCase(usuarioLogado.getPerfil())) {
            return consultaRepository.findAll();
        }
        
        
        //Se for DENTISTA, faz o vínculo inteligente pelo E-mail
        if ("DENTISTA".equalsIgnoreCase(usuarioLogado.getPerfil())) {
            Dentista dentista = dentistaRepository.findByEmail(usuarioLogado.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Nenhum perfil de Dentista encontrado com o e-mail deste usuário."));
            
            return consultaRepository.findByDentistaId(dentista.getId());
        }
        
        
        //   Se for perfil Comum (ex: recepcionista), pode ver as consultas que ele mesmo registrou
        return consultaRepository.findByUsuarioId(usuarioLogado.getId());
    
    }
    public java.util.Optional<Consulta> buscarPorId(Integer id) {
        return consultaRepository.findById(id);
 
    }

    //Método para atualizar (reagendar) uma consulta
    public Consulta atualizarConsulta(Consulta consulta) {
        if (consulta.getDataInicio().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Erro: Não é possível reagendar para o passado.");
    
        }
        
        
        if (consulta.getDataEnding().isBefore(consulta.getDataInicio()) || consulta.getDataEnding().isEqual(consulta.getDataInicio())) {
            throw new IllegalArgumentException("Erro: O horário de término deve ser posterior ao horário de início.");
        }
        
        List<Consulta> conflitos = consultaRepository.findConflitosHorario(
                consulta.getDentista().getId(),
                consulta.getDataInicio(),
                consulta.getDataEnding()
        );
        
        
        boolean conflitoReal = conflitos.stream().anyMatch(c -> !c.getId().equals(consulta.getId()));
        if (conflitoReal) {
            throw new IllegalArgumentException("Erro: O dentista selecionado já possui outra consulta marcada para este horário.");
        }
        return consultaRepository.save(consulta);
    }
 
    
    // A  rotina vai rodar automaticamente a cada 1 hora (3600000 milissegundos)
    // Para testar agora, você pode trocar por 10000 (10 segundos)
    
    @Scheduled(fixedDelay = 3600000) 
    public void rotinaFinalizarConsultas() {
        // Dispara o update no banco de dados passando a data e hora exatas deste segundo
        consultaRepository.finalizarConsultasPassadas(LocalDateTime.now());
        System.out.println("Rotina executada: Consultas antigas foram marcadas como FINALIZADA.");
    }
    
}
