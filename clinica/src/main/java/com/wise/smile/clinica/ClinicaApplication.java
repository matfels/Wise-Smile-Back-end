package com.wise.smile.clinica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wise.smile.clinica.entity.Usuario;
import com.wise.smile.clinica.repositories.UsuarioRepositories;

@SpringBootApplication
public class ClinicaApplication {
	
	
	@Autowired
	private UsuarioRepositories usuarioRepositories;
	

	public static void main(String[] args) {
		SpringApplication.run(ClinicaApplication.class, args);
	}

	
}
