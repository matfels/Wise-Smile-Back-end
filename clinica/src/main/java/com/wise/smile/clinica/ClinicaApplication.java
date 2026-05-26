package com.wise.smile.clinica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wise.smile.clinica.entity.Usuario;
import com.wise.smile.clinica.repositories.UsuarioRepositories;

@SpringBootApplication
public class ClinicaApplication implements CommandLineRunner{
	
	@Autowired
	private UsuarioRepositories usuarioRepositories;
	

	public static void main(String[] args) {
		SpringApplication.run(ClinicaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	//	Usuario usuario1 = new Usuario("Matheus Melo2", "10568319887","matheuw@matheus.com.br", "1234", "AGENDADA", true, null, null);		
	//	usuarioRepositories.save(usuario1);
		//lisita de usuarios
		List<Usuario> listaDeUsuario = usuarioRepositories.findAll();
		listaDeUsuario.forEach(usuario -> System.out.println(usuario));
	
	}

}
