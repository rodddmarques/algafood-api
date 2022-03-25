package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.DadosInformadosJaExistenteException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadesController {

	@Autowired
	CidadeRepository cidadeRepository;
	
	@Autowired
	CadastroCidadeService cidadeService;
	
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public List<Cidade> listar() {
		return cidadeRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
		try {
			cidade = cidadeService.salvar(cidade);
			return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (DadosInformadosJaExistenteException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	@GetMapping("/{idCidade}")
	public ResponseEntity<Cidade> buscar(@PathVariable Long idCidade) {
		
		Optional<Cidade> cidade = cidadeRepository.findById(idCidade);
		
		if (cidade.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(cidade.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{idCidade}")
	public ResponseEntity<?> alterar(@PathVariable Long idCidade, @RequestBody Cidade cidade) {
		Optional<Cidade> cidadeEncontrada = cidadeRepository.findById(idCidade);
		try {
			if (cidadeEncontrada.isPresent()) {
				BeanUtils.copyProperties(cidade, cidadeEncontrada.get(), "id");
				return ResponseEntity.status(HttpStatus.OK).body(cidadeService.salvar(cidadeEncontrada.get()));
			}
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (DadosInformadosJaExistenteException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
	@DeleteMapping("/{idCidade}")
	public ResponseEntity<?> remover(@PathVariable long idCidade) {
		Optional<Cidade> cidadeEncontrada = cidadeRepository.findById(idCidade);
		
		if (cidadeEncontrada.isPresent()) {
			cidadeService.deletarCidade(cidadeEncontrada.get());
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
}

