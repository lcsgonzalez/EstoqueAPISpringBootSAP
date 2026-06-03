package com.example.teste.config;

import com.example.teste.repository.UsuarioRepository;
import com.example.teste.usuario.Role;
import com.example.teste.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;
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

        if (usuarioRepository.findByLoginAndAtivoTrue("Administrador do Sistema").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setLogin("Administrador do Sistema");
            admin.setSenha(passwordEncoder.encode("Administrador do Sistema"));
            admin.setRole(Role.ADMINISTRADOR_DO_SISTEMA);
            admin.setAtivo(true);
            usuarioRepository.save(admin);
            System.out.println("Usuário 'Administrador do Sistema'criado: Administrador do Sistema / Administrador do Sistema");
        }


        if (usuarioRepository.findByLoginAndAtivoTrue("Operador de almoxarifado").isEmpty()) {
            Usuario user = new Usuario();
            user.setLogin("Operador de almoxarifado");
            user.setSenha(passwordEncoder.encode("Operador de almoxarifado"));
            user.setRole(Role.OPERADOR_DE_ALMOXARIFADO);
            user.setAtivo(true);
            usuarioRepository.save(user);
            System.out.println("Usuário 'Operador de almoxarifado' criado: Operador de almoxarifado / Operador de almoxarifado");
        }
    }
}
