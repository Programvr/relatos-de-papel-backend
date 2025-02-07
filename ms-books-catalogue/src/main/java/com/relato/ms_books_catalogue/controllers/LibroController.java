package com.relato.ms_books_catalogue.controllers;

import com.relato.ms_books_catalogue.exceptions.BookNotFoundException;
import com.relato.ms_books_catalogue.models.Libro;
import com.relato.ms_books_catalogue.services.LibroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroController {
    @Autowired
    private LibroService service;

    @GetMapping
    public List<Libro> getAllBooks() {
        return service.getAllBooks();
    }

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody Libro libro) {
        try {
            service.addBook(libro);
            return ResponseEntity.ok("Libro registrado correctamente.");
        } catch (BookNotFoundException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public List<Libro> searchBooks(@RequestParam String keyword) {
        return service.searchBooks(keyword);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        service.deleteBook(id);
    }

    @GetMapping("/{id}/cantidad")
    public ResponseEntity<Integer> getBookQuantityById(@PathVariable Long id) {
        Integer cantidad = service.getBookQuantityById(id);
        return ResponseEntity.ok(cantidad);
    }

    @PutMapping("/{id}/cantidad")
    public void updateBookQuantity(@PathVariable Long id, @RequestParam Integer cantidad) {
         service.updateBookQuantity(id, cantidad);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> getBookById(@PathVariable Long id) {
        try {
            Libro libro = service.getBookById(id);
            return ResponseEntity.ok(libro);
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}