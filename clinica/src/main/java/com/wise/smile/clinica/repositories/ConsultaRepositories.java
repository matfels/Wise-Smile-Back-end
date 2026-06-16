package com.wise.smile.clinica.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wise.smile.clinica.entity.Consulta;

import jakarta.transaction.Transactional;

@Repository
public interface ConsultaRepositories extends JpaRepository<Consulta, Integer> {
    
	
	
    // Metodo para ajudar a validar a regra de conflito de horarios para o dentista
    @Query("SELECT c FROM Consulta c WHERE c.dentista.id = :dentistaId AND " +
           "((c.dataInicio BETWEEN :inicio AND :fim) OR (c.dataEnding BETWEEN :inicio AND :fim))")
    List<Consulta> findConflitosHorario(@Param("dentistaId") Integer dentistaId, 
                                        @Param("inicio") LocalDateTime inicio, 
                                        @Param("fim") LocalDateTime fim);

    @Modifying
    @Transactional
    @Query("UPDATE Consulta c SET c.status = 'FINALIZADA' WHERE c.status = 'AGENDADA' AND c.dataInicio < :agora")
    void finalizarConsultasPassadas(@Param("agora") LocalDateTime agora);
}