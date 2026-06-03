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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private AutenticacaoService autenticacaoService;
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/login").permitAll();

                    req.requestMatchers(HttpMethod.GET, "/cursos", "/cursos/*", "/cursos/periodos").hasAnyRole("USER", "ADMIN");
                    req.requestMatchers(HttpMethod.POST, "/cursos").hasAnyRole("USER", "ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/cursos").hasAnyRole("USER", "ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/cursos/*").hasRole(Role.ADMIN.name());

                    req.requestMatchers("/usuarios/**").hasRole(Role.ADMIN.name());

                    req.anyRequest().authenticated();
                })
                .userDetailsService(autenticacaoService)
                .build();
    }
}
