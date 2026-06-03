package com.example.teste.segurity;

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

                    // Rotas de produtos
                    req.requestMatchers(HttpMethod.GET, "/produtos/**").hasAnyRole("USER", "ADMIN");
                    req.requestMatchers(HttpMethod.POST, "/produtos").hasAnyRole("USER", "ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/produtos/**").hasAnyRole("USER", "ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/produtos/**").hasRole("ADMIN");

                    // Rotas de movimentação
                    req.requestMatchers(HttpMethod.GET, "/movimentacoes", "/movimentacoes/*").hasAnyRole("USER", "ADMIN");
                    req.requestMatchers(HttpMethod.POST, "/movimentacoes").hasAnyRole("USER", "ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/movimentacoes").hasAnyRole("USER", "ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/movimentacoes/*").hasRole(Role.ADMIN.name());

                    // Rotas de gerenciamento de usuários (apenas ADMIN)
                    req.requestMatchers("/usuarios/**").hasRole(Role.ADMIN.name());

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