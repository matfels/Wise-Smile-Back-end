package com.wise.smile.clinica.service;

import com.wise.smile.clinica.entity.Paciente;
import com.wise.smile.clinica.repositories.PacienteRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService{

    @Autowired
    private PacienteRepositories pacienteRepository;

    public Paciente registarPaciente(Paciente paciente) {
        //  Verifica se o CPF já existe
        if (pacienteRepository.findByCpf(paciente.getCpf()).isPresent()) {
            throw new IllegalArgumentException("Erro: Já existe um paciente registado com este CPF.");
        }
        
        //Verifica se o E-mail j existe
        if (pacienteRepository.findByEmail(paciente.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Erro: Já existe um paciente registado com este E-mail.");
        }
        
        return pacienteRepository.save( paciente);
    }

    public List<Paciente> listarTodos()  {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> buscarPorId(Integer id) {
        return pacienteRepository.findById(id);
    }

    public void deletarPaciente(Integer id)  {
        pacienteRepository.deleteById(id);
    }
}