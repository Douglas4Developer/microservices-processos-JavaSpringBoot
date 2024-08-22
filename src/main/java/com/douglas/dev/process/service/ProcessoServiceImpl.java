package com.douglas.dev.process.service;

import com.douglas.dev.process.dto.ProcessoDTO;
import com.douglas.dev.process.dto.ReuDTO;
import com.douglas.dev.process.exception.ProcessoDuplicadoException;
import com.douglas.dev.process.exception.ProcessoNotFoundException;
import com.douglas.dev.process.exception.ProcessoValidacaoNumero;
import com.douglas.dev.process.exception.ReuNomeVazioException;
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
        // Verifica se o número do processo é nulo ou vazio
        if (dto.getNumero() == null || dto.getNumero().trim().isEmpty()) {
            throw new ProcessoValidacaoNumero("Erro: O número do processo não pode estar vazio.");
        }
        // Verifica se o número do processo é válido (maior que zero)
        try {
            long numeroProcesso = Long.parseLong(dto.getNumero());
            if (numeroProcesso <= 0) {
                throw new ProcessoValidacaoNumero("Erro: O número do processo deve ser maior que zero.");
            }
        } catch (NumberFormatException e) {
            throw new ProcessoValidacaoNumero("Erro: O número do processo deve ser um número válido.");
        }

        // Verifica se o processo já está cadastrado
        if (repository.findByNumero(dto.getNumero()).isPresent()) {
            throw new ProcessoDuplicadoException("Erro: Processo já cadastrado");
        }

        // Cria e salva o novo processo
        Processo processo = new Processo();
        processo.setNumero(dto.getNumero());
        return repository.save(processo);
    }


    @Override
    public Processo adicionarReu(String numero, ReuDTO reuDTO) throws IOException {
        if (reuDTO.getNome() == null || reuDTO.getNome().trim().isEmpty()) {
            throw new ReuNomeVazioException("Erro: O nome do réu não pode estar vazio.");
        }

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
