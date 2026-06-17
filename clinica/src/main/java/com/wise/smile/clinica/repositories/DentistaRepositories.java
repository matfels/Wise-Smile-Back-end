package com.wise.smile.clinica.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wise.smile.clinica.entity.Dentista;

@Repository
public interface DentistaRepositories extends JpaRepository<Dentista, Integer> {
    Optional<Dentista> findByCpf(String cpf);
    Optional<Dentista> findByCro(String cro);   
    Optional<Dentista> findByEmail(String email);
    
    @Query(value = "SELECT DISTINCT d.* FROM dentista d INNER JOIN dentista_especialidade de ON d.id = de.id_dentista WHERE de.id_especialidade = :idEspecialidade", nativeQuery = true)
    List<Dentista> buscarPorEspecialidadeId(@Param("idEspecialidade") Integer idEspecialidade);
   
    
}





