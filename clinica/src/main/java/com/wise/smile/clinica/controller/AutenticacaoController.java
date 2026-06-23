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

@RestController // Define que esta classe é um controlador REST (recebe requisições HTTP e devolve JSON)
@RequestMapping("/login") // Define que o endereço base para acessar os métodos desta classe é "/login"
public class AutenticacaoController {

    @Autowired // Injeta automaticamente a dependência do Spring Security responsável por gerenciar a autenticação
    private AuthenticationConfiguration configuration;
    // O "Gerente" que verifica a senha

    @Autowired // Injeta o serviço que criamos para manipular e gerar os tokens JWT
    private TokenService tokenService;
    // A "Fábrica de Crachás"

    
    // Mapeia requisições do tipo POST para este método (usado para enviar dados sensíveis, como senhas)
    public ResponseEntity efetuarLogin(@RequestBody DadosAutenticacao dados) { // @RequestBody extrai o JSON da requisição para o objeto 'dados'
        
        try {
            //Pega no email e senha do envelope e empacota no formato que o Spring entende
            var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
            
            // Pega o gerenciador de autenticação para procurar no banco e verificar o hash BCrypt da senha
            AuthenticationManager manager = configuration.getAuthenticationManager() ;
            var authentication = manager.authenticate(authenticationToken);
            
            // Se a senha estiver correta, isolamos o objeto inteiro do usuário autenticado
            Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
            
            // Gera o Token JWT contendo as informações do usuário
            var tokenJWT = tokenService.gerarToken(usuarioLogado);
            
            // Retorna o status 200 (OK) enviando o Token gerado e o ID do usuário para o front-end
            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT, usuarioLogado.getId()));
            
        } catch (Exception e) {
            // Caso falhe (senha errada ou usuário não encontrado), devolve status 400 (Bad Request)
            return ResponseEntity.badRequest().body("Acesso Negado: Usuário ou senha incorretos.");
        }
    }
    
}