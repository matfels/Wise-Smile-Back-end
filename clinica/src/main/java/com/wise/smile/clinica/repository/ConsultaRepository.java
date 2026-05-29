package com.wise.smile.clinica.repository;

import com.wise.smile.clinica.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {


}