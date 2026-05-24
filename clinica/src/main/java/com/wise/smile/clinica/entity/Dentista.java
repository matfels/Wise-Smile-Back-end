package com.wise.smile.clinica.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "dentista")
@Data
public class Dentista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 6)
    private String cro;

    @Column(columnDefinition = "boolean default true")
    private Boolean ativo;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @ManyToMany
    @JoinTable(
        name = "dentista_especialidade",
        joinColumns = @JoinColumn(name = "id_dentista"),
        inverseJoinColumns = @JoinColumn(name = "id_especialidade")
    )
    private List<Especialidade> especialidades;

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
        if (this.ativo == null) {
            this.ativo = true;
        }
    }
}