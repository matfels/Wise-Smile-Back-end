package com.wise.smile.clinica.repositories;

import com.wise.smile.clinica.entity.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadeRepositories extends JpaRepository<Especialidade, Integer> {
}