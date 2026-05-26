package com.wise.smile.clinica.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity // Avisa o java que essa classe não e apenas um código e sim que representa uma tabela real na base de dados.
@Table(name = "usuarios")
@Data



public class Usuario {

	// Criei um construtor vazio para poder trazer os dados para a listano (ClinicaAplicattion).
	public Usuario() {
	}
	
	
	public Usuario(String nome, String cpf, String email, String senha, String perfil, Boolean ativo,
			LocalDateTime dataCriacao, LocalDateTime ultimoLogin) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
		this.perfil = perfil;
		this.ativo = ativo;
		this.dataCriacao = dataCriacao;
		this.ultimoLogin = ultimoLogin;
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


	@Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String senha;

    @Column(nullable = false, length = 100)
    private String perfil;

    @Column(columnDefinition = "boolean default true")
    private Boolean ativo;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;

    @Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", email=" + email + ", senha=" + senha
				+ ", perfil=" + perfil + ", ativo=" + ativo + ", dataCriacao=" + dataCriacao + ", ultimoLogin="
				+ ultimoLogin + "]";
	}


	// @PrePersist para preencher a data de criação automaticamente antes de salvar
    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
        if (this.ativo == null) {
            this.ativo = true;
        }
    }
}