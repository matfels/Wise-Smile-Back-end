package com.wise.smile.clinica;

import com.wise.smile.clinica.entity.Paciente;
import com.wise.smile.clinica.entity.Usuario;
import com.wise.smile.clinica.repository.PacienteRepository;
import com.wise.smile.clinica.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClinicaApplicationTests {
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private PacienteRepository pacienteRepository;
	@Test
	void testCrudUsuarioEPaciente() {
		//     TESTE USUÁRIO
		
		Usuario usuario = new Usuario();
		usuario.setNome("Matheus Melo 3");
		usuario.setCpf("12345378992");
		usuario.setEmail("matheus@masadtheuswss.com");
		usuario.setSenha("admin123"); // No futuro usaremos BCrypt aqui [cite: 133, 198]
		usuario.setPerfil("ADMIN");
		usuario.setAtivo(true);

		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		assertNotNull(usuarioSalvo.getId());
		System.out.println("Usuário salvo: " + usuarioSalvo.getNome());

		// 2. Buscar
		Optional<Usuario> usuarioBuscado = usuarioRepository.findById(usuarioSalvo.getId());
		assertTrue(usuarioBuscado.isPresent());
		System.out.println("Usuário encontrado pelo ID: " + usuarioBuscado.get().getNome());

		// TESTE PACIENTE

		// 1.Salvar
		Paciente paciente = new Paciente();
		paciente.setNome("Paciente de Teste");
		paciente.setEmail("teste.paciente@gmail.com");
		paciente.setCpf("98765432100");
		paciente.setTelefone("41999999999");


		Paciente pacienteSalvo = pacienteRepository.save(paciente);
		assertNotNull(pacienteSalvo.getId());
		System.out.println("Paciente salvo: " + pacienteSalvo.getNome());

		//2. Listar Todos
		long totalPacientes = pacienteRepository.count();
		assertTrue(totalPacientes > 0);
		System.out.println("Total de pacientes no banco: " + totalPacientes);

		// 3. DELETE
		// Remover apenas o paciente 
		pacienteRepository.deleteById(pacienteSalvo.getId());
		Optional<Paciente> aposDeletar = pacienteRepository.findById(pacienteSalvo.getId());
		assertFalse(aposDeletar.isPresent());
		System.out.println("Paciente removido com sucesso!");
	}
}