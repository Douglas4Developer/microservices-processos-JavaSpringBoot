package com.douglas.dev.process.controller;

import com.douglas.dev.process.dto.ProcessoDTO;
import com.douglas.dev.process.exception.ProcessoDuplicadoException;
import com.douglas.dev.process.model.Processo;
import com.douglas.dev.process.service.ProcessoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/processos")
@RequiredArgsConstructor
public class ProcessoController {

    private final ProcessoService service;

    @PostMapping
    public ResponseEntity<Processo> salvar(@Valid @RequestBody ProcessoDTO dto) {
        try {
            Processo processo = service.salvar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(processo);
        } catch (ProcessoDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Outros endpoints como excluir, consultar, etc.
}