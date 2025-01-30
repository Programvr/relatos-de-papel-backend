package com.relato.ms_books_payments.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.relato.ms_books_payments.models.Compras;

@Repository
public interface ComprasRepository extends JpaRepository<Compras, Long> {
}