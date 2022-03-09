package io.github.wyllian.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.wyllian.domain.entity.Usuario;

public interface UsuarioRepository  extends JpaRepository< Usuario, Integer>{
    
    Optional<Usuario> findByLogin(String login);
}
