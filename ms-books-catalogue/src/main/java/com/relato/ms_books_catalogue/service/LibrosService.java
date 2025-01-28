package com.relato.ms_books_catalogue.service;

import com.relato.ms_books_catalogue.exception.BookNotFoundException;
import com.relato.ms_books_catalogue.model.Libros;
import com.relato.ms_books_catalogue.repository.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class LibrosService {
    @Autowired
    private LibrosRepository repository;

    public List<Libros> getAllBooks() {
        return repository.findAll();
    }

    public Libros addBook(Libros libros) {
        return repository.save(libros);
    }

    public List<Libros> searchBooks(String keyword) {
        return repository.searchBooks(keyword);
    }

    public Optional<Integer> getBookQuantityById(Long id) {
        return repository.findById(id)
                         .map(Libros::getCantidad);
    }

    public Libros getBookById(Long id) {
        Optional<Libros> libro = repository.findById(id);
        if (libro.isPresent()) {
            return libro.get();
        } else {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
    }

    public void deleteBook(Long id) {
        repository.deleteById(id);
    }

    public void updateBookQuantity(Long id, Integer cantidad) {
         repository.findById(id)
                         .map(libros -> {
                             libros.setCantidad(cantidad);
                             return repository.save(libros);
                         });
    }
}