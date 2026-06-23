package com.wise.smile.clinica.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wise.smile.clinica.entity.Consulta;
import com.wise.smile.clinica.entity.Dentista;
import com.wise.smile.clinica.entity.Paciente;
import com.wise.smile.clinica.entity.StatusConsulta;
import com.wise.smile.clinica.entity.Usuario;
import com.wise.smile.clinica.repositories.ConsultaRepositories;
import com.wise.smile.clinica.repositories.DentistaRepositories;
import com.wise.smile.clinica.repositories.PacienteRepositories;

@Service // Marca a classe como um Componente de 
//. Aqui ficam as "Regras de Negócio"
public class ConsultaService {

    @Autowired // Injeta as classes Repositories para comunicar com o banco de dados
    private ConsultaRepositories consultaRepository;

    @Autowired
    private DentistaRepositories dentistaRepository;

    @Autowired
    private PacienteRepositories pacienteRepository;
    
    // Marcar uma nova consulta
    public Consulta agendarConsulta(Consulta consulta) {
        

        if (consulta.getDataInicio().isBefore(LocalDateTime.now())) { // Verifica se a data de início é anterior ao dia/hora atual
            throw new IllegalArgumentException("Erro: Não é possível agendar consultas em datas passadas.");
        }

        //O horário final da consulta deve ser após o horário inicial
        if (consulta.getDataEnding().isBefore(consulta.getDataInicio()) || consulta.getDataEnding().isEqual(consulta.getDataInicio())) { // Impede consultas que terminem antes de começar ou no exato momento
            throw new IllegalArgumentException ("Erro: O horário de término deve ser posterior ao horário de início.");
        }

        //Não permitir conflito de horário para odentista
        // Utilizamos metodo e criamos no ConsultaRepositories
        List<Consulta> conflitos = consultaRepository.findConflitosHorario( // Vai no banco e busca se esse dentista já tem consulta nesse mesmo intervalo de hora
                consulta.getDentista().getId(),
                consulta.getDataInicio(),
                consulta.getDataEnding()
        );

        if (!conflitos.isEmpty()){ // Se a lista não vier vazia, existe choque de horários!
            throw new IllegalArgumentException("Erro: O dentista selecionado já possui uma consulta marcada para este horário.");
        }

        // Passando emaem todas as validações, definimos o status inicial e guardamos
        consulta.setStatus(StatusConsulta.AGENDADA);
        return consultaRepository.save(consulta); // Salva no banco e retorna o objeto final
    }
    
    // Método para cancelar uma consulta
    public Consulta cancelarConsulta (Integer idConsulta, String motivoCancelamento){
        
        // Vai buscar a consulta à base de dados ou lança erro se não existir
        Consulta consulta = consultaRepository.findById(idConsulta) // Busca pelo ID ou aborta jogando uma exceção
                .orElseThrow(() -> new IllegalArgumentException("Erro: Consulta não encontrada."));

        // Regra  Para cancelar consultas é necessário um motivo
        if (motivoCancelamento == null || motivoCancelamento.trim().isEmpty()) { // Valida se o texto não tá nulo nem com espaços em branco apenas
            throw new IllegalArgumentException("Erro: O motivo do cancelamento é obrigatório.") ;
        }

        // Atualiza os dados e guarda na base de dados
        consulta.setStatus(StatusConsulta.CANCELADA);
        consulta.setMotivoCancelamento(motivoCancelamento);
        
        return consultaRepository.save(consulta);
        
        
    }
    
    
    // FILTRA A CONSULTA POR PERFIL (ADMIN, DENTISTA, USUARIO).
    public List<Consulta> listarTodas(Usuario usuarioLogado) {
        
        // Se for ADMIN, traz absolutamente todas as consultas do sistema
        if ("ADMIN".equalsIgnoreCase(usuarioLogado.getPerfil())) { // Ignora se o 'ADMIN' vier com maiúsculas/minúsculas
            return consultaRepository.findAll();
        }
        
        
        //Se for DENTISTA, faz o vínculo inteligente pelo E-mail
        if ("DENTISTA".equalsIgnoreCase(usuarioLogado.getPerfil())) {
            Dentista dentista = dentistaRepository.findByEmail(usuarioLogado.getEmail()) // Tenta descobrir quem é o Dentista olhando o e-mail do usuário logado
                .orElseThrow(() -> new IllegalArgumentException("Nenhum perfil de Dentista encontrado com o e-mail deste usuário."));
            
            return consultaRepository.findByDentistaId(dentista.getId());
        }
        
        // Se for perfil COMUM, busca o paciente pelo e-mail e retorna as consultas vinculadas
        Paciente paciente = pacienteRepository.findByEmail(usuarioLogado.getEmail()) // Como os pacientes não se cadastram pelo dentista mas sim pelo sistema, o e-mail liga a pessoa à conta
            .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado para este usuário."));
            
        return consultaRepository.findByPacienteId(paciente.getId());
    
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
        
        
        // Pega todos os possíveis conflitos. Se houver algum conflito onde o ID do conflito seja DIFERENTE da consulta atual, é um conflito real
        boolean conflitoReal = conflitos.stream().anyMatch(c -> !c.getId().equals(consulta.getId())); 
        if (conflitoReal) {
            throw new IllegalArgumentException("Erro: O dentista selecionado já possui outra consulta marcada para este horário.");
        }
        return consultaRepository.save(consulta);
    }
 
    
    // A  rotina vai rodar automaticamente a cada 1 hora (3600000 milissegundos)
    // Para testar agora, você pode trocar por 10000 (10 segundos)
    
    @Scheduled(fixedDelay = 3600000) // Spring Boot aciona esse método sozinho periodicamente (como um "robô" de cron)
    public void rotinaFinalizarConsultas() {
        // Dispara o update no banco de dados passando a data e hora exatas deste segundo
        consultaRepository.finalizarConsultasPassadas(LocalDateTime.now());
        System.out.println("Rotina executada: Consultas antigas foram marcadas como FINALIZADA.");
    }
    
}