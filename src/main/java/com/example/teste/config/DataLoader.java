package com.example.teste.config;


import com.example.teste.repository.UsuarioRepository;
import com.example.teste.usuario.Role;
import com.example.teste.usuario.Usuario;
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
        if (usuarioRepository.findByLoginAndAtivoTrue("administrador").isEmpty()) {
            Usuario administrador = new Usuario();
            administrador.setLogin("administrador");
            administrador.setSenha(passwordEncoder.encode("administrador"));
            administrador.setRole(Role.ADMINISTRADOR);
            administrador.setAtivo(true);
            usuarioRepository.save(administrador);
            System.out.println("Usuário ADMININISTRADOR criado: administrador / administrador");
        }

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
