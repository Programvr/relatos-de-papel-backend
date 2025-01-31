package com.relato.ms_books_auth.services;

import com.relato.ms_books_auth.exceptions.UsuarioException;
import com.relato.ms_books_auth.models.Usuarios;
import com.relato.ms_books_auth.repositorys.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacionesService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuarios registrarUsuario(Usuarios usuarios) {
        if (usuariosRepository.existsByCorreo(usuarios.getCorreo())) {
            throw new UsuarioException("El correo ya está registrado");
        }
        usuarios.setContrasena(passwordEncoder.encode(usuarios.getContrasena()));
        return usuariosRepository.save(usuarios);
    }

    public Optional<Usuarios> loginUsuario(String correo, String contrasena) {
        Optional<Usuarios> usuario = usuariosRepository.findByCorreo(correo);
        if (usuario.isEmpty()) {
            throw new UsuarioException("El usuario no existe");
        }
        if (passwordEncoder.matches(contrasena, usuario.get().getContrasena())) {
            return usuario;
        }
        return Optional.empty();
    }
}
