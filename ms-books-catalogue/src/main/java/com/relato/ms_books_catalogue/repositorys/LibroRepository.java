package com.relato.ms_books_catalogue.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.relato.ms_books_catalogue.models.Libro;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Query("SELECT l FROM Libro l WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(l.autor) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(l.genero) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
    List<Libro> searchBooks(@Param("keyword") String keyword);

    boolean existsByTituloAndAutor(String titulo, String autor);
}