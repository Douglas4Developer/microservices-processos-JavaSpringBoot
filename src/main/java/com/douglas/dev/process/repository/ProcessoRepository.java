package com.douglas.dev.process.repository;

import com.douglas.dev.process.model.Processo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {
    Optional<Processo> findByNumero(String numero);
}