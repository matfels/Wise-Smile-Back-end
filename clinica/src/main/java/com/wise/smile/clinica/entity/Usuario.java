package com.wise.smile.clinica.entity;

import java.time.LocalDateTime;
import java.util.Collection; // <-- IMPORTANTE: Faltava esse cara!
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
	
// Avisa ao Spring/JPA que esta classe representa uma entidade/tabela real do banco de dados
@Entity 

@Table(name = "usuarios") // Define explicitamente que o nome da tabela gerada no banco será "usuarios"
@Data // Anotação do Lombok que gera automaticamente os Getters, Setters e o método toString invisíveis
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // Blindando falhas de serialização (transformação em JSON) quando há carregamento tardio
public class Usuario implements UserDetails { // Implementar UserDetails diz ao Spring Security que isso é um "Usuário" do sistema dele
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

    @Id // Marca o campo como a Chave Primária do banco de dados (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O banco de dados vai gerar o ID automaticamente usando auto-incremento
    private Integer id;


	@Column(nullable = false, length = 100) // Campo não pode ser vazio e tem limite de 100 caracteres
    private String nome;

    @Column(nullable = false, unique = true, length = 14) // 'unique' garante que não haverá dois CPFs iguais
    private String cpf;

    @Column(nullable = false, unique = true, length = 100) // E-mail também deve ser único e é usado como Login
    private String email;

    @Column(nullable = false, length = 100)
    private String senha;

    @Column(nullable = false, length = 100) // Cargo do usuário no sistema (ex: ADMIN, USER, DENTISTA)
    private String perfil;

    @Column(columnDefinition = "boolean default true") // Impede que comece nulo no banco e assume 'true'
    private Boolean ativo;

    @Column(name = "data_criacao", updatable = false) // Data que foi criado (não pode ser atualizada via updates)
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
    @PrePersist // Um "gatilho" do JPA. Esse método executa SOZINHO imediatamente antes de inserir a linha no banco de dados.
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now(); // Grava a hora exata da criação
        if (this.ativo == null) {
            this.ativo = true;
        }
    }


//------------------------ METÓDOS EXIGIDOS PELA INTERFACE UserDetails ------------------------
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // Define as "permissões" (Roles) baseadas no perfil do banco
    	if ("ADMIN".equalsIgnoreCase(this.perfil)) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() { // Diz ao Spring Security de onde vem a senha
        return this.senha;
    }

    @Override
    public String getUsername() { // Diz ao Spring Security qual campo é usado como "nome de usuário" (no caso, o email)
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() { // Conta não expira
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // Conta não está bloqueada
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // As credenciais/senha não expiraram
        return true;
    }
    
    
    @Override
    public boolean isEnabled() { // Diz se o usuário está ativo no sistema para permitir o login
        // Trava contra NullPointerException no Spring Security + Blindagem
        if (this.ativo == null) {
            return false;
        }
        return this.ativo;
    }


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	public String getPerfil() {
		return perfil;
	}


	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}


	public Boolean getAtivo() {
		return ativo;
	}


	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}


	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}


	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}


	public LocalDateTime getUltimoLogin() {
		return ultimoLogin;
	}


	public void setUltimoLogin(LocalDateTime ultimoLogin) {
		this.ultimoLogin = ultimoLogin;
	}   
    
    
}