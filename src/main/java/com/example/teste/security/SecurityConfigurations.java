package com.example.teste.security;

import com.example.teste.service.AutenticacaoService;
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
import com.example.teste.usuario.Role;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Habilita anotações como @PreAuthorize nos controllers
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter; // Filtro JWT personalizado

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())                // Desabilita CSRF (API stateless)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sem sessão
                .authorizeHttpRequests(req -> {
                    // Endpoint público de login
                    req.requestMatchers("/login").permitAll();

                    // Rotas de cursos
                    req.requestMatchers(HttpMethod.GET, "/produtos", "/produtos/*").hasAnyRole("OP", "ADMIN");
                    req.requestMatchers(HttpMethod.POST, "/produtos", "/produtos/*").hasAnyRole("OP", "ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/produtos", "/produtos/*").hasAnyRole("OP", "ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/produtos/*").hasAnyRole("ADMIN");

                    // Qualquer outra requisição precisa estar autenticada
                    req.anyRequest().authenticated();
                })
                .userDetailsService(autenticacaoService)
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Filtro JWT antes do padrão
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Já gera e armazena o salt automaticamente
    }
}
