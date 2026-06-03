package com.example.teste.security;


import com.example.teste.service.AutenticacaoService;
import com.example.teste.usuario.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sem sessão
                .authorizeHttpRequests(req -> {
                    // Endpoint público de login
                    req.requestMatchers("/login").permitAll();

                    // Rotas de cursos
                    req.requestMatchers(HttpMethod.GET, "/produtos", "/produtos/*", "/movimentacoes").hasAnyRole("OPERADOR", "ADMINISTRADOR");
                    req.requestMatchers(HttpMethod.POST, "/produtos", "/movimentacoes").hasAnyRole("OPERADOR", "ADMINISTRADOR");
                    req.requestMatchers(HttpMethod.PUT, "/produtos","/movimentacoes").hasAnyRole("OPERADOR", "ADMINISTRADOR");
                    req.requestMatchers(HttpMethod.DELETE, "/produtos/*").hasRole(Role.ADMINISTRADOR.name());

                    // Rotas de gerenciamento de usuários (apenas ADMIN)
                    req.requestMatchers("/usuarios/**").hasRole(Role.ADMINISTRADOR.name());

                    // Qualquer outra requisição precisa estar autenticada
                    req.anyRequest().authenticated();
                })
                .userDetailsService(autenticacaoService)
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
