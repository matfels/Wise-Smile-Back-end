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
}