package com.wise.smile.clinica.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "especialidades")
@Data
public class Especialidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nome;

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

	@Override
	public String toString() {
		return "Especialidade [id=" + id + ", nome=" + nome + "]";
	}

    
}