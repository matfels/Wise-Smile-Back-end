package com.wise.smile.clinica.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.wise.smile.clinica.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service // Registra que essa classe é um serviço que será injetado em outros lugares (como no AutenticacaoController)
public class TokenService {

    // Definindo "senha mestra" para assinar os tokens. 
    
	private String secret = "12345678"; 

    // Fabricamos o cracha quando o utilizador faz login
   
	public String gerarToken(Usuario usuario) {
        try {
            //Define o algoritmo de criptografia e a senha mestra
            Algorithm algoritmo = Algorithm.HMAC256(secret); // Algoritmo robusto usado pelo JWT
            
            return JWT.create()
                    .withIssuer("API Wise Smile") //Quem emitiu o token
                    .withSubject(usuario.getEmail() ) // A quem pertence o token (guardamos o email)
                    .withExpiresAt(dataExpiracao() ) // Data e hora onde o token perde a validade e obriga um novo login
                    .sign(algoritmo); //Carimba a assinatura digital
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    
	}

    //Metodo que VALIDA o crachá quando o utilizador tenta aceder a uma rota protegida
    public String getSubject(String tokenJWT) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Wise Smile") // Garante que foi nossa API quem emitiu (segurança contra tokens falsos)
                    .build()
                    .verify(tokenJWT) // Tenta abrir o token com a senha mestra
                    .getSubject(); // Devolve o email que estava guardado lá dentro
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
      
        }
    }

    // Define 2 horas de validade do token após ser gerado.
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")); // Pega o fuso do Brasil (-03:00) e soma +2h para o futuro
   
    }
}