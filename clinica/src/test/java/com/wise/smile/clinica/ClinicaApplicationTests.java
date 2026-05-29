package com.wise.smile.clinica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wise.smile.clinica.entity.Consulta;
import com.wise.smile.clinica.entity.Dentista;
import com.wise.smile.clinica.entity.Especialidade;
import com.wise.smile.clinica.entity.Paciente;
import com.wise.smile.clinica.entity.StatusConsulta;
import com.wise.smile.clinica.entity.Usuario;
import com.wise.smile.clinica.repository.ConsultaRepository;
import com.wise.smile.clinica.repository.DentistaRepository;
import com.wise.smile.clinica.repository.EspecialidadeRepository;
import com.wise.smile.clinica.repository.PacienteRepository;
import com.wise.smile.clinica.repository.UsuarioRepository;

@SpringBootTest
class ClinicaApplicationTests {
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private PacienteRepository pacienteRepository;
	@Autowired
	private EspecialidadeRepository especialidadeRepository;

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private DentistaRepository dentistaRepository;
	@Test
	void testCrudUsuarioEPaciente() {
		//     TESTE USUÁRIO
		
		Usuario usuario = new Usuario();
		usuario.setNome("Jo2se a443lindo3");
		usuario.setCpf("12245352692");
		usuario.setEmail("jos2@a56indo.com");
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
		paciente.setNome("Pacien4e 4de Teste");
		paciente.setEmail("teste.pda5asçlfkciente@gmail.com");
		paciente.setCpf("98365437100");
		paciente.setTelefone("45995999999");


		Paciente pacienteSalvo = pacienteRepository.save(paciente);
		assertNotNull(pacienteSalvo.getId());
		System.out.println("Paciente salvo: " + pacienteSalvo.getNome());

		//2. Listar Todos
		long totalPacientes = pacienteRepository.count();
		assertTrue(totalPacientes > 0);
		System.out.println("Total de pacientes no banco: " + totalPacientes);


		// 1. TESTE ESPECIALIDADE
	    Especialidade esp = new Especialidade();
	    esp.setNome("Ortodontia");
	    Especialidade espSalva = especialidadeRepository.save(esp);
	    assertNotNull(espSalva.getId());
	    System.out.println("Especialidade salva: " + espSalva.getNome());

	    // 2. PREPARAÇÃO PARA CONSULTA (Dependências)
	    // Criando um Dentista para a consulta
	    Dentista dentista = new Dentista();
	    dentista.setNome("Ddr. Wilson");
	    dentista.setCpf("11122233744");
	    dentista.setEmail("wilson@clini3a.com");
	    dentista.setCro("12345");
	    dentista.setAtivo(true);
	    dentista.setEspecialidades(java.util.List.of(espSalva)); // Relacionamento ManyToMany
	    dentistaRepository.save(dentista);

	    // Reutilizando um Usuário e Paciente (certifique-se de que existem ou crie novos)
	    Usuario admin = usuarioRepository.findAll().get(0); 
	    Paciente paciente2 = pacienteRepository.findAll().get(0);

	    // 3. TESTE CONSULTA (O "Caminho das Pedras")
	    Consulta consulta = new Consulta();
	    consulta.setPaciente(paciente);
	    consulta.setDentista(dentista);
	    consulta.setUsuario(admin);
	    consulta.setDescricao("Consulta de rotina para manutenção de aparelho");
	    consulta.setDataInicio(java.time.LocalDateTime.now().plusDays(1)); // Amanhã
	    consulta.setDataEnding(java.time.LocalDateTime.now().plusDays(1).plusHours(1));
	    consulta.setStatus(StatusConsulta.AGENDADA);

	    Consulta consultaSalva = consultaRepository.save(consulta);
	    assertNotNull(consultaSalva.getId());
	    assertEquals(StatusConsulta.AGENDADA, consultaSalva.getStatus());
	    System.out.println("Consulta marcada com sucesso para o paciente: " + consultaSalva.getPaciente().getNome());

	    // 4. TESTE FIND E DELETE
	    assertTrue(consultaRepository.findById(consultaSalva.getId()).isPresent());
	    consultaRepository.deleteById(consultaSalva.getId());
	    assertFalse(consultaRepository.findById(consultaSalva.getId()).isPresent());
	    System.out.println("Teste de exclusão de consulta concluído.");
	}
}