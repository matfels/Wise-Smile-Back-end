package com.wise.smile.clinica.repositories;

import com.wise.smile.clinica.entity.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DentistaRepositories extends JpaRepository<Dentista, Integer> {
    Optional<Dentista> findByCpf(String cpf);
    Optional<Dentista> findByCro(String cro);
}