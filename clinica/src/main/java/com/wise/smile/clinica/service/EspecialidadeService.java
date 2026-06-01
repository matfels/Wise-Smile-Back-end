package com.wise.smile.clinica.service;

import com.wise.smile.clinica.entity.Especialidade;
import com.wise.smile.clinica.repositories.EspecialidadeRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadeService {
	

    @Autowired
    private EspecialidadeRepositories especialidadeRepository;

    public Especialidade salvar(Especialidade especialidade)  {
        return especialidadeRepository.save(especialidade);
    }

    public List<Especialidade> listarTodas() {
        return especialidadeRepository.findAll();
    }

    
    public Optional<Especialidade> buscarPorId( Integer id)  {
        return especialidadeRepository.findById(id);
    }
    
}