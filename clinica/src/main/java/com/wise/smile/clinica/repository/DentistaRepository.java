package com.wise.smile.clinica.repository;

import com.wise.smile.clinica.entity.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DentistaRepository extends JpaRepository<Dentista, Integer> {


}