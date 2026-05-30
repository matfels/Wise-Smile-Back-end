package com.wise.smile.clinica;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wise.smile.clinica.entity.Usuario;
import com.wise.smile.clinica.repositories.ConsultaRepositories;
import com.wise.smile.clinica.repositories.DentistaRepositories;
import com.wise.smile.clinica.repositories.EspecialidadeRepositories;
import com.wise.smile.clinica.repositories.PacienteRepositories;
import com.wise.smile.clinica.repositories.UsuarioRepositories;
import com.wise.smile.clinica.service.UsuarioService;

@SpringBootTest
class ClinicaApplicationTests {
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
	@Autowired
	private UsuarioRepositories usuarioRepository;
	@Autowired
	private PacienteRepositories pacienteRepository;
	@Autowired
	private EspecialidadeRepositories especialidadeRepository;

	@Autowired
	private ConsultaRepositories consultaRepository;

	@Autowired
	private DentistaRepositories dentistaRepository;
	@Test
	void testUsuarioServiceCompleto() {
	    System.out.println("--- A INICIAR TESTE COMPLETO DO USUARIO SERVICE ---");

	    // ==========================================
	    // Registo com Sucesso 
	    // ==========================================
	    Usuario novoUsuario = new Usuario();
	    novoUsuario.setNome("Matfels");
	    novoUsuario.setCpf("56845325598"); // Tem de ser único na base de dados
	    novoUsuario.setEmail("Mat.Fels@wisesmile.com"); // Tem de ser único na base de dados
	    novoUsuario.setSenha("admin");
	    novoUsuario.setPerfil("DENTISTA");
	    novoUsuario.setAtivo(true);

	    Usuario usuarioSalvo = usuarioService.registarUsuario(novoUsuario);
	    assertNotNull(usuarioSalvo.getId(), "O ID não deveria ser nulo após salvar");
	    System.out.println("1. Sucesso: Utilizador registado com o ID " + usuarioSalvo.getId());

	    // ==========================================
	    //  Validação da Encriptação (BCrypt)
	    // ==========================================
	    // Verifica se a palavra-passe guardada NÃO é a palavra-passe em texto limpo
	    assertNotEquals("senhaSecreta123", usuarioSalvo.getSenha());
	    // Verifica se o hash gerado corresponde à palavra-passe original
	    assertTrue(passwordEncoder.matches("senhaSecreta123", usuarioSalvo.getSenha()));
	    System.out.println("2. Sucesso: Palavra-passe encriptada validada -> " + usuarioSalvo.getSenha());

	    // ==========================================
	    //  Validação de E-mail Duplicado
	    // ==========================================
	    Usuario utilizadorEmailDuplicado = new Usuario();
	    utilizadorEmailDuplicado.setNome("Novo Usuario");
	    utilizadorEmailDuplicado.setCpf("33333333333"); // CPF diferente
	    utilizadorEmailDuplicado.setEmail("Novo.Usuario@wisesmile.com"); // EMAIL IGUAL
	    utilizadorEmailDuplicado.setSenha("12345");
	    utilizadorEmailDuplicado.setPerfil("ADMIN");

	    // O assertThrows verifica se o código realmente "explode" (lança erro) quando tentamos gravar
	    IllegalArgumentException erroEmail = assertThrows(IllegalArgumentException.class, () -> {
	        usuarioService.registarUsuario(utilizadorEmailDuplicado);
	    });
	    System.out.println("3. Sucesso: Erro de e-mail capturado -> " + erroEmail.getMessage());

	    // ==========================================
	    //Validação de CPF Duplicado
	    // ==========================================
	    Usuario utilizadorCpfDuplicado = new Usuario();
	    utilizadorCpfDuplicado.setNome("Outro Clone do Carlos");
	    utilizadorCpfDuplicado.setCpf("99988877766"); // CPF IGUAL
	    utilizadorCpfDuplicado.setEmail("novo.email@wisesmile.com"); // EMAIL DIFERENTE
	    utilizadorCpfDuplicado.setSenha("12345");
	    utilizadorCpfDuplicado.setPerfil("ADMIN");

	    IllegalArgumentException erroCpf = assertThrows(IllegalArgumentException.class, () -> {
	        usuarioService.registarUsuario(utilizadorCpfDuplicado);
	    });
	    System.out.println("4. Sucesso: Erro de CPF capturado -> " + erroCpf.getMessage());

	    System.out.println("--- TESTE COMPLETO FINALIZADO COM SUCESSO ---");
	}
}