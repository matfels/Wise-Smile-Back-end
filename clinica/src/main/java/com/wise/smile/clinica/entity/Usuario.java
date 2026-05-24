package com.wise.smile.clinica.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity // Avisa o java que essa classe não e apenas um código e sim que representa uma tabela real na base de dados.
@Table(name = "usuarios")
@Data
public class Usuario {

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

    // @PrePersist para preencher a data de criação automaticamente antes de salvar
    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
        if (this.ativo == null) {
            this.ativo = true;
        }
    }
}