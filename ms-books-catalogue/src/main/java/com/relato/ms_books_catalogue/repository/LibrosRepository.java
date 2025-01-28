package com.relato.ms_books_catalogue.repository;

import com.relato.ms_books_catalogue.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibrosRepository extends JpaRepository<Libros, Long> {
    @Query("SELECT l FROM Libros l WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(l.autor) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(l.genero) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(l.isbn) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Libros> searchBooks(@Param("keyword") String keyword);
}