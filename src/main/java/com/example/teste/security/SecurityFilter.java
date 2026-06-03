package com.example.teste.security;

import com.example.teste.service.AutenticacaoService;
import com.example.teste.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.stereotype.Component;

@Component
public class SecurityFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private AutenticacaoService autenticacaoService;
}
