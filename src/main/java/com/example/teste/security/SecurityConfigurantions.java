package com.example.teste.security;

import com.example.teste.service.AutenticacaoService;
import com.example.teste.usuario.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static com.example.teste.usuario.Role.ADMINISTRADOR_DO_SISTEMA;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfigurantions {
    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {

                    req.requestMatchers("/login").permitAll();

                    req.requestMatchers(HttpMethod.GET, "/produtos", "/produtos/*")
                            .hasAnyRole("OPERADOR_DE_ALMOXARIFADO", "ADMINISTRADOR_DO_SISTEMA");

                    req.requestMatchers(HttpMethod.POST, "/produtos")
                            .hasAnyRole("OPERADOR_DE_ALMOXARIFADO", "ADMINISTRADOR_DO_SISTEMA");

                    req.requestMatchers(HttpMethod.PUT, "/produtos/*")
                            .hasAnyRole("OPERADOR_DE_ALMOXARIFADO", "ADMINISTRADOR_DO_SISTEMA");

                    req.requestMatchers(HttpMethod.DELETE, "/produtos/*")
                            .hasRole("ADMINISTRADOR_DO_SISTEMA");

                    req.anyRequest().authenticated();
                })
                .userDetailsService(autenticacaoService)
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();


    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
