package com.relato.ms_books_auth.services;

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
        usuarios.setContrasena(passwordEncoder.encode(usuarios.getContrasena()));
        return usuariosRepository.save(usuarios);
    }

    public Optional<Usuarios> loginUsuario(String correo, String contrasena) {
        Optional<Usuarios> usuarios = usuariosRepository.findByCorreo(correo);
        if (usuarios.isPresent() && passwordEncoder.matches(contrasena, usuarios.get().getContrasena())) {
            return usuarios;
        }
        return Optional.empty();
    }
}
