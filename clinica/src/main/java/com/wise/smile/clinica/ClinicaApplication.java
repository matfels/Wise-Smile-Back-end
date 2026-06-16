package com.wise.smile.clinica;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wise.smile.clinica.entity.Usuario;
import com.wise.smile.clinica.repositories.UsuarioRepositories;

@SpringBootApplication
@EnableScheduling // <- motor de rotina

public class ClinicaApplication {
	
	
	@Autowired
	private UsuarioRepositories usuarioRepositories;
	

	public static void main(String[] args) {
		SpringApplication.run(ClinicaApplication.class, args);
	}

	
}
