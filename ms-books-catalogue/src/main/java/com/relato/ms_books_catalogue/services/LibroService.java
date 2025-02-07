package com.relato.ms_books_catalogue.services;

import com.relato.ms_books_catalogue.exceptions.BookNotFoundException;
import com.relato.ms_books_catalogue.models.Libro;
import com.relato.ms_books_catalogue.repositorys.LibroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class LibroService {
    @Autowired
    private LibroRepository repository;

    public List<Libro> getAllBooks() {
        return repository.findAll();
    }

    public Libro addBook(Libro libro) {
        boolean exists = repository.existsByTituloAndAutor(libro.getTitulo(), libro.getAutor());
        if (exists) {
            throw new BookNotFoundException("Libro, su titulo y autor ya existen.");
        }
        return repository.save(libro);
    }

    public List<Libro> searchBooks(String keyword) {
        return repository.searchBooks(keyword);
    }

    public Integer getBookQuantityById(Long id) {
        return repository.findById(id)
                         .map(Libro::getCantidad)
                         .orElse(0);
    }

    public Libro getBookById(Long id) {
        Optional<Libro> libro = repository.findById(id);
        if (libro.isPresent()) {
            return libro.get();
        } else {
            throw new BookNotFoundException("Libro no encontrado con este id: " + id);
        }
    }

    public void deleteBook(Long id) {
        repository.deleteById(id);
    }

    public void updateBookQuantity(Long id, Integer cantidad) {
         repository.findById(id)
                         .map(libro -> {
                             libro.setCantidad(cantidad);
                             return repository.save(libro);
                         });
    }
}