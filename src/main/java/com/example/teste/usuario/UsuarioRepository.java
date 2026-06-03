package com.example.teste.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLoginAndAtivoTrue(String login);
    boolean existsByLoginAndAtivoTrue(String login);
    Page<Usuario> findAllByAtivoTrue(Pageable pageable);
    Optional<Usuario> findByIdAndAtivoTrue(Long id);
}
