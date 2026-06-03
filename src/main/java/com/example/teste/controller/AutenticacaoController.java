package com.example.teste.controller;

import com.example.teste.autenticacao.DadosAutenticacao;
import com.example.teste.autenticacao.DadosTokenJWT;
import com.example.teste.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DadosTokenJWT> login(@RequestBody @Valid DadosAutenticacao dados) {
        // Cria um token de autenticação com as credenciais fornecidas
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                dados.login(), 
                dados.senha()
        );

        // Autentica o usuário
        Authentication autenticacao = authenticationManager.authenticate(token);

        // Obtém o usuário autenticado e gera o token JWT
        var usuario = (com.example.teste.model.Usuario) autenticacao.getPrincipal();
        String tokenJWT = tokenService.gerarToken(usuario);

        // Retorna o token
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
