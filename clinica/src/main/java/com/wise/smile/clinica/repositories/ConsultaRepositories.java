package com.wise.smile.clinica.repositories;

import com.wise.smile.clinica.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultaRepositories extends JpaRepository<Consulta, Integer> {
    
    // Método para ajudar a validar a regra de conflito de horários para o mesmo dentista
    @Query("SELECT c FROM Consulta c WHERE c.dentista.id = :dentistaId AND " +
           "((c.dataInicio BETWEEN :inicio AND :fim) OR (c.dataEnding BETWEEN :inicio AND :fim))")
    List<Consulta> findConflitosHorario(@Param("dentistaId") Integer dentistaId, 
                                        @Param("inicio") LocalDateTime inicio, 
                                        @Param("fim") LocalDateTime fim);
}