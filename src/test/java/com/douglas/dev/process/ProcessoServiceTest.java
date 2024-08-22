package com.douglas.dev.process;

import com.douglas.dev.process.dto.ProcessoDTO;
import com.douglas.dev.process.dto.ReuDTO;
import com.douglas.dev.process.exception.ProcessoDuplicadoException;
import com.douglas.dev.process.exception.ProcessoNotFoundException;
import com.douglas.dev.process.model.Processo;
import com.douglas.dev.process.repository.ProcessoRepository;
import com.douglas.dev.process.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProcessoServiceTest {

	@Mock
	private ProcessoRepository repository;

	@InjectMocks
	private ProcessoServiceImpl service;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void salvar_deveSalvarProcesso() throws IOException {
		ProcessoDTO dto = new ProcessoDTO("12345");
		Processo processo = new Processo(1L, "12345", new ArrayList<>());

		when(repository.save(any(Processo.class))).thenReturn(processo);
		when(repository.findByNumero(dto.getNumero())).thenReturn(Optional.empty());

		Processo salvo = service.salvar(dto);

		assertNotNull(salvo);
		assertEquals("12345", salvo.getNumero());
		verify(repository, times(1)).save(any(Processo.class));
	}

	@Test
	void salvar_deveLancarExcecaoQuandoProcessoDuplicado() {
		ProcessoDTO dto = new ProcessoDTO("12345");
		when(repository.findByNumero(dto.getNumero())).thenReturn(Optional.of(new Processo()));

		assertThrows(ProcessoDuplicadoException.class, () -> service.salvar(dto));
	}

	@Test
	void adicionarReu_deveAdicionarReuAoProcesso() throws IOException {
		ReuDTO reuDTO = new ReuDTO("João Silva");
		Processo processo = new Processo(1L, "12345", new ArrayList<>());

		when(repository.findByNumero("12345")).thenReturn(Optional.of(processo));
		when(repository.save(any(Processo.class))).thenReturn(processo);

		Processo atualizado = service.adicionarReu("12345", reuDTO);

		assertEquals(1, atualizado.getReus().size());
		assertEquals("João Silva", atualizado.getReus().get(0).getNome());
	}

	@Test
	void consultarPorNumero_deveRetornarProcesso() throws IOException {
		Processo processo = new Processo(1L, "12345", new ArrayList<>());
		when(repository.findByNumero("12345")).thenReturn(Optional.of(processo));

		Processo encontrado = service.consultarPorNumero("12345");

		assertNotNull(encontrado);
		assertEquals("12345", encontrado.getNumero());
	}

	@Test
	void consultarPorNumero_deveLancarExcecaoQuandoNaoEncontrado() {
		when(repository.findByNumero("12345")).thenReturn(Optional.empty());

		assertThrows(ProcessoNotFoundException.class, () -> service.consultarPorNumero("12345"));
	}

	@Test
	void excluir_deveExcluirProcesso() throws IOException {
		Processo processo = new Processo(1L, "12345", new ArrayList<>());
		when(repository.findByNumero("12345")).thenReturn(Optional.of(processo));

		service.excluir("12345");

		verify(repository, times(1)).delete(processo);
	}
}
