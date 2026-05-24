package com.wise.smile.clinica.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
@Data
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_dentista", nullable = false)
    private Dentista dentista;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "motivo_cancelamento", columnDefinition = "TEXT")
    private String motivoCancelamento;

    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDateTime dataEnding;

    @Column(name = "data_registro", updatable = false)
    private LocalDateTime dataRegistro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusConsulta status;

    @PrePersist
    protected void onCreate() {
        this.dataRegistro = LocalDateTime.now();
        if (this.status == null) {
            this.status = StatusConsulta.AGENDADA;
        }
    }
}