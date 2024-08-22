package com.douglas.dev.process.service;

import com.douglas.dev.process.dto.ProcessoDTO;
import com.douglas.dev.process.dto.ReuDTO;
import com.douglas.dev.process.model.Processo;

import java.io.IOException;
import java.util.List;

public interface ProcessoService {

    Processo salvar(ProcessoDTO dto) throws IOException;

    Processo adicionarReu(String numero, ReuDTO reuDTO) throws IOException;

    List<Processo> listarTodos() throws IOException;

    Processo consultarPorNumero(String numero) throws IOException;

    void excluir(String numero) throws IOException;

    List<ReuDTO> consultarReus(String numero) throws IOException;
}
