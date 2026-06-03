package com.example.teste.config;

import com.example.teste.usuario.Role;
import com.example.teste.usuario.Usuario;
import com.example.teste.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Criar usuário ADMIN se não existir
        if (usuarioRepository.findByLoginAndAtivoTrue("admin").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setLogin("admin");
            admin.setSenha(passwordEncoder.encode("admin"));
            admin.setRole(Role.ADMIN);
            admin.setAtivo(true);
            usuarioRepository.save(admin);
            System.out.println("Usuário ADMIN criado: admin / admin");
        }

        // Criar usuário OPERADOR se não existir
        if (usuarioRepository.findByLoginAndAtivoTrue("operador").isEmpty()) {
            Usuario operador = new Usuario();
            operador.setLogin("operador");
            operador.setSenha(passwordEncoder.encode("operador"));
            operador.setRole(Role.OPERADOR);
            operador.setAtivo(true);
            usuarioRepository.save(operador);
            System.out.println("Usuário OPERADOR criado: operador / operador");
        }
    }
}