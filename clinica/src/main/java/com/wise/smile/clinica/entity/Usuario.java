package com.wise.smile.clinica.entity;

import java.time.LocalDateTime;
import java.util.Collection; // <-- IMPORTANTE: Faltava esse cara!
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
	
@Entity // Avisa o java que essa classe não e apenas um código e sim que representa uma tabela real na base de dados.
@Table(name = "usuarios")
@Data




public class Usuario implements UserDetails {
	
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


//------------------------
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if ("ADMIN".equals(this.perfil)) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
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