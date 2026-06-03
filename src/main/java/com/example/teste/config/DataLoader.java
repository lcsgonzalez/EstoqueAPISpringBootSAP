package com.example.teste.config;

import com.example.teste.usuario.Role;
import com.example.teste.model.Usuario;
import com.example.teste.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@ComponentScan("com.example.teste.config")
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

        // Criar usuário user se não existir
        if (usuarioRepository.findByLoginAndAtivoTrue("user").isEmpty()) {
            Usuario user = new Usuario();
            user.setLogin("user");
            user.setSenha(passwordEncoder.encode("user"));
            user.setRole(Role.USER);
            user.setAtivo(true);
            usuarioRepository.save(user);
            System.out.println("Usuário user criado: user / OPERADOR");
        }
    }
}
