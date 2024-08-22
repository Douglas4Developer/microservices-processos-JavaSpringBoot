package com.douglas.dev.process.controller;

import com.douglas.dev.process.dto.ProcessoDTO;
import com.douglas.dev.process.dto.ReuDTO;
import com.douglas.dev.process.model.Processo;
import com.douglas.dev.process.service.ProcessoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/processos")
@RequiredArgsConstructor
@Tag(name = "Processos", description = "Gerenciamento de Processos e Réus")
public class ProcessoController {

    private final ProcessoService service;

    @Operation(summary = "Salva um novo processo", description = "Salva um novo processo no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Processo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Processo duplicado")
    })
    @PostMapping("/salvar")
    public ResponseEntity<Processo> salvar(@Valid @RequestBody ProcessoDTO dto) throws IOException {
        Processo processo = service.salvar(dto);
        return new ResponseEntity<>(processo, HttpStatus.CREATED); // Retorna com status 201 Created
    }

    @Operation(summary = "Adiciona um réu a um processo", description = "Adiciona um réu ao processo especificado pelo número")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Réu adicionado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Processo não encontrado")
    })
    @PutMapping("/atualizar/{numero}/reus")
    public ResponseEntity<Processo> adicionarReu(@PathVariable String numero, @RequestBody ReuDTO reuDTO) throws IOException {
        Processo processo = service.adicionarReu(numero, reuDTO);
        return ResponseEntity.ok(processo); // Retorna com status 200 OK
    }

    @Operation(summary = "Lista todos os processos", description = "Retorna uma lista de todos os processos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de processos retornada com sucesso")
    @GetMapping("/listar")
    public ResponseEntity<List<Processo>> listarTodos() throws IOException {
        List<Processo> processos = service.listarTodos();
        return ResponseEntity.ok(processos); // Retorna com status 200 OK
    }

    @Operation(summary = "Consulta um processo pelo número", description = "Retorna os detalhes de um processo específico pelo número")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processo encontrado"),
            @ApiResponse(responseCode = "404", description = "Processo não encontrado")
    })
    @GetMapping("/consultar/{numero}")
    public ResponseEntity<Processo> consultarPorNumero(@PathVariable String numero) throws IOException {
        Processo processo = service.consultarPorNumero(numero);
        return ResponseEntity.ok(processo); // Retorna com status 200 OK
    }

    @Operation(summary = "Consulta os réus de um processo", description = "Retorna uma lista de réus associados a um processo específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de réus retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Processo não encontrado")
    })
    @GetMapping("/consultar/{numero}/reus")
    public ResponseEntity<List<ReuDTO>> consultarReus(@PathVariable String numero) throws IOException {
        List<ReuDTO> reus = service.consultarReus(numero);
        return ResponseEntity.ok(reus); // Retorna com status 200 OK
    }

    @Operation(summary = "Exclui um processo", description = "Exclui um processo pelo número fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processo excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Processo não encontrado")
    })
    @DeleteMapping("/excluir/{numero}")
    public ResponseEntity<String> excluir(@PathVariable String numero) throws IOException {
        service.excluir(numero);
        return ResponseEntity.ok("Processo excluído com sucesso"); // Retorna com status 200 OK e mensagem de sucesso
    }
}
