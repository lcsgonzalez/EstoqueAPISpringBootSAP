package com.example.teste.repository;


import com.example.teste.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLoginAndAtivoTrue(String login);
    boolean existsByLoginAndAtivoTrue(String login);
    Page<Usuario> findAllByAtivoTrue(Pageable pageable);
    Optional<Usuario> findByIdAndAtivoTrue(Long id);
}
