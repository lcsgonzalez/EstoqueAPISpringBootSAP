package br.com.senai.config;
import br.com.senai.usuarios.Roles;
import br.com.senai.usuarios.Usuario;
import br.com.senai.usuarios.UsuarioRepository;
import lombok.NonNull;
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
    public void run(String @NonNull ... args) {
        if (usuarioRepository.findByUsernameAndAtivoTrue("admin").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(Roles.ADMIN);
            admin.setAtivo(true);
            usuarioRepository.save(admin);
            System.out.println("Usuário ADMIN criado: admin / admin");
        }

       if (usuarioRepository.findByUsernameAndAtivoTrue("user").isEmpty()) {
            Usuario user = new Usuario();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRole(Roles.OPERADOR);
            user.setAtivo(true);
            usuarioRepository.save(user);
            System.out.println("Usuário USER criado: user / user");
        }
    }
}
