package com.example.teste.controller;

import com.example.teste.autenticacao.DadosAutenticacao;
import com.example.teste.autenticacao.DadosToken;
import com.example.teste.service.TokenService;
import com.example.teste.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;
    @PostMapping
    public ResponseEntity<DadosToken> login(@RequestBody @Valid DadosAutenticacao dados){
        var authToken = new UsernamePasswordAuthenticationToken(dados.login(),dados.senha());
        var authentication = manager.authenticate(authToken);
        var usuario = (Usuario) authentication.getPrincipal();
        var token = tokenService.gerarToken(usuario);
        return ResponseEntity.ok(new DadosToken(token));
    }
}
