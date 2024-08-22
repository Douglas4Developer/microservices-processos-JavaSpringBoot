package com.douglas.dev.process;

import com.douglas.dev.process.dto.ProcessoDTO;
import com.douglas.dev.process.dto.ReuDTO;
import com.douglas.dev.process.model.Processo;
import com.douglas.dev.process.repository.ProcessoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")  // Anotação para usar o profile de teste
class ProcessoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProcessoRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll(); // Limpa o repositório antes de cada teste
    }

    @Test
    void deveSalvarProcesso() throws Exception {
        ProcessoDTO processoDTO = new ProcessoDTO("12345");

        mockMvc.perform(post("/v1/processos/salvar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(processoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numero").value("12345"));
    }

    @Test
    void deveAdicionarReuAoProcesso() throws Exception {
        // Primeiro, salvar um processo
        Processo processo = new Processo(null, "12345", new ArrayList<>());
        repository.save(processo);

        ReuDTO reuDTO = new ReuDTO("João Silva");

        mockMvc.perform(put("/v1/processos/atualizar/12345/reus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(reuDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reus[0].nome").value("João Silva"));
    }

    @Test
    void deveListarTodosProcessos() throws Exception {
        Processo processo1 = new Processo(null, "12345", new ArrayList<>());
        Processo processo2 = new Processo(null, "67890", new ArrayList<>());
        repository.save(processo1);
        repository.save(processo2);

        mockMvc.perform(get("/v1/processos/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].numero").value("12345"))
                .andExpect(jsonPath("$[1].numero").value("67890"));
    }

    @Test
    void deveConsultarProcessoPorNumero() throws Exception {
        Processo processo = new Processo(null, "12345", new ArrayList<>());
        repository.save(processo);

        mockMvc.perform(get("/v1/processos/consultar/12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero").value("12345"));
    }

    @Test
    void deveExcluirProcesso() throws Exception {
        Processo processo = new Processo(null, "12345", new ArrayList<>());
        repository.save(processo);

        // Verifica se o processo foi salvo
        assertTrue(repository.findByNumero("12345").isPresent());

        mockMvc.perform(delete("/v1/processos/excluir/12345"))
                .andExpect(status().isOk())
                .andExpect(content().string("Processo excluído com sucesso"));

        // Verifica se o processo foi excluído
        assertFalse(repository.findByNumero("12345").isPresent());
    }

    @Test
    void deveLancarExcecaoQuandoProcessoNaoEncontrado() throws Exception {
         // Executa a requisição e espera que o status retornado seja 404 (Not Found)
        mockMvc.perform(get("/v1/processos/consultar/12345"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Processo não encontrado"));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

