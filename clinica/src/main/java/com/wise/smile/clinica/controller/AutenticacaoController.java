package com.wise.smile.clinica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wise.smile.clinica.dto.DadosAutenticacao;
import com.wise.smile.clinica.dto.DadosTokenJWT;
import com.wise.smile.clinica.entity.Usuario;
import com.wise.smile.clinica.service.TokenService;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationConfiguration configuration; // O "Gerente" que verifica a senha

    @Autowired
    private TokenService tokenService; // A nossa "Fábrica de Crachás"

    
    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody DadosAutenticacao dados) throws Exception {
        
        // Pega no email e senha do envelope e empacota no formato que o Spring entende
    	var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        //Pega AutenticacaoService, procurar no banco e verificar o BCrypt
    	AuthenticationManager manager = configuration.getAuthenticationManager();
        var authentication = manager.authenticate(authenticationToken);
        // senha está correta vamos gerar o Token JWT
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        
        //dEVOLVE o Token dentro do envelope de resposta
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}