package com.wise.smile.clinica.service;
import java.util.List;
import com.wise.smile.clinica.entity.Dentista;
import com.wise.smile.clinica.repositories.DentistaRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DentistaService {

    @Autowired
    private DentistaRepositories dentistaRepository;

    public Dentista registarDentista(Dentista dentista){
        // Verifica se o CRO ja existe
        if (dentistaRepository.findByCro(dentista.getCro()).isPresent()) {
            throw new IllegalArgumentException("Erro: Já existe um dentista registado com este CRO.");
        }
        
        //  Verifica se o CPF ja existe
        if (dentistaRepository.findByCpf(dentista.getCpf()).isPresent()){
            throw new IllegalArgumentException("Erro: Já existe um dentista registado com este CPF.");
        }
        
        return dentistaRepository.save(dentista);
    }

    public List<Dentista> listarTodos() {
        return dentistaRepository.findAll();
    }

    public Optional<Dentista> buscarPorId(Integer id)  {
        return dentistaRepository.findById(id);
    }
    
    public void deletarDentista(Integer id) {
        // Busca o dentista, muda o status e salva por cima (Exclusão Lógica)
        var dentista = dentistaRepository.findById(id).get();
        dentista.setAtivo(false);
        dentistaRepository.save(dentista);
    }
    
    public Dentista atualizarDentista(Dentista dentista) {
        return dentistaRepository.save(dentista);
    }
    
    public List<Dentista> buscarPorEspecialidadeId(Integer idEspecialidade) {
        return dentistaRepository.buscarPorEspecialidadeId(idEspecialidade);
    }
    public void ativarDentista(Integer id) {
        // Busca o dentista, muda o status para verdadeiro (true) e salva por cima
        var dentista = dentistaRepository.findById(id).get();
        dentista.setAtivo(true);
        dentistaRepository.save(dentista);
    }
}