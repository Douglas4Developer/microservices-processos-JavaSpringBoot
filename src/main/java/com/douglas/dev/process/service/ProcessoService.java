package com.douglas.dev.process.service;

import com.douglas.dev.process.dto.ProcessoDTO;
import com.douglas.dev.process.exception.ProcessoDuplicadoException;
import com.douglas.dev.process.model.Processo;
import com.douglas.dev.process.repository.ProcessoRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProcessoService {

    private final ProcessoRepository repository;

    public Processo salvar(ProcessoDTO dto) throws ProcessoDuplicadoException {
        if (repository.findByNumero(dto.getNumero()).isPresent()) {
            throw new ProcessoDuplicadoException("Processo já cadastrado");
        }
        Processo processo = new Processo();
        processo.setNumero(dto.getNumero());
        return repository.save(processo);
    }

    // Outros métodos como excluir, consultar, adicionar réu, etc.
}