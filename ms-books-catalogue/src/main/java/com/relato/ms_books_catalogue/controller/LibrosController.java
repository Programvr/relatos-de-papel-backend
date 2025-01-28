package com.relato.ms_books_catalogue.controller;

import com.relato.ms_books_catalogue.exception.BookNotFoundException;
import com.relato.ms_books_catalogue.model.Libros;
import com.relato.ms_books_catalogue.service.LibrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibrosController {
    @Autowired
    private LibrosService service;

    @GetMapping
    public List<Libros> getAllBooks() {
        return service.getAllBooks();
    }

    @PostMapping
    public Libros addBook(@RequestBody Libros libros) {
        return service.addBook(libros);
    }

    @GetMapping("/search")
    public List<Libros> searchBooks(@RequestParam String keyword) {
        return service.searchBooks(keyword);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        service.deleteBook(id);
    }

    @GetMapping("/{id}/cantidad")
    public ResponseEntity<Integer> getBookQuantityById(@PathVariable Long id) {
    return service.getBookQuantityById(id)
                  .map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(0));
    }

    @PutMapping("/{id}/cantidad")
    public void updateBookQuantity(@PathVariable Long id, @RequestParam Integer cantidad) {
         service.updateBookQuantity(id, cantidad);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libros> getBookById(@PathVariable Long id) {
        try {
            Libros libro = service.getBookById(id);
            return ResponseEntity.ok(libro);
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}