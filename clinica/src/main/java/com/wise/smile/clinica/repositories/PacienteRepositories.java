package com.wise.smile.clinica.repositories;

import com.wise.smile.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
    Optional<Paciente> findByCpf(String cpf);
    Optional<Paciente> findByEmail(String email);
}