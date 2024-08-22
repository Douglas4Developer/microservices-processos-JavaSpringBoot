package com.douglas.dev.process.service;

import com.douglas.dev.process.dto.ProcessoDTO;
import com.douglas.dev.process.dto.ReuDTO;
import com.douglas.dev.process.exception.ProcessoDuplicadoException;
import com.douglas.dev.process.exception.ProcessoNotFoundException;
import com.douglas.dev.process.model.Processo;
import com.douglas.dev.process.model.Reu;
import com.douglas.dev.process.repository.ProcessoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessoServiceImpl implements ProcessoService {

    private final ProcessoRepository repository;

    @Override
    public Processo salvar(ProcessoDTO dto) throws IOException {
        if (repository.findByNumero(dto.getNumero()).isPresent()) {
            throw new ProcessoDuplicadoException("Erro: Processo já cadastrado");
        }
        Processo processo = new Processo();
        processo.setNumero(dto.getNumero());
        return repository.save(processo);
    }

    @Override
    public Processo adicionarReu(String numero, ReuDTO reuDTO) throws IOException {
        Processo processo = repository.findByNumero(numero)
                .orElseThrow(() -> new ProcessoNotFoundException("Processo não encontrado"));
        Reu reu = new Reu();
        reu.setNome(reuDTO.getNome());
        reu.setProcesso(processo);
        processo.getReus().add(reu);
        return repository.save(processo);
    }

    @Override
    public List<Processo> listarTodos() throws IOException {
        return repository.findAll();
    }

    @Override
    public Processo consultarPorNumero(String numero) throws IOException {
        return repository.findByNumero(numero)
                .orElseThrow(() -> new ProcessoNotFoundException("Processo não encontrado"));
    }


    @Override
    public void excluir(String numero) throws IOException {
        Processo processo = repository.findByNumero(numero)
                .orElseThrow(() -> new ProcessoNotFoundException("Processo não encontrado"));
        repository.delete(processo);
    }

    @Override
    public List<ReuDTO> consultarReus(String numero) throws IOException {
        Processo processo = repository.findByNumero(numero)
                .orElseThrow(() -> new ProcessoNotFoundException("Processo não encontrado"));
        return processo.getReus().stream()
                .map(reu -> new ReuDTO(reu.getNome()))
                .toList();
    }
}
