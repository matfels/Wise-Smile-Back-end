package com.wise.smile.clinica.repositories;

import com.wise.smile.clinica.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositories extends JpaRepository<Usuario, Integer>{
	Optional<Usuario> findByEmail(String email);
	Optional<Usuario> findByCpf(String cpf);

	
	
}
