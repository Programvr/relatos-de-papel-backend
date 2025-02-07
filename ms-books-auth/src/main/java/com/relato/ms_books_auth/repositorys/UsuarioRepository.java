package com.relato.ms_books_auth.repositorys;

import com.relato.ms_books_auth.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    
    boolean existsByCorreo(String correo);

}
