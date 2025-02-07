package com.relato.ms_books_payments.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.relato.ms_books_payments.models.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
}