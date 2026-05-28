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
		Usuario usuario1 = new Usuario("Matheus Melo4", "23568319887","matheuca@matheus.com.br", "1234", "AGENDADA", true, null, null);		
		usuarioRepositories.save(usuario1); //Salva no banco de dados
		//lisita de usuarios
		usuario1.setNome("João Paulo");
		usuario1.setCpf("68495025559");
		usuario1.setEmail("joao.paulo@joao.com.br");
		usuarioRepositories.save(usuario1);
		
		//impressão do usuario no terminal.
		List<Usuario> listaDeUsuario = usuarioRepositories.findAll();
		listaDeUsuario.forEach(usuario -> System.out.println(usuario));
	
	}

}
