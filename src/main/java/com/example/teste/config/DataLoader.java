package com.example.teste.config;


import com.example.teste.usuarios.Role;
import com.example.teste.usuarios.Usuario;
import com.example.teste.usuarios.UsuarioRepository;
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

        // Criar usuário USER se não existir
        if (usuarioRepository.findByLoginAndAtivoTrue("user").isEmpty()) {
            Usuario operadorAlmoxarifado = new Usuario();
            operadorAlmoxarifado.setLogin("user");
            operadorAlmoxarifado.setSenha(passwordEncoder.encode("user"));
            operadorAlmoxarifado.setRole(Role.USER);
            operadorAlmoxarifado.setAtivo(true);
            usuarioRepository.save(operadorAlmoxarifado);
            System.out.println("Usuário USER criado: user / user");
        }
    }
}
