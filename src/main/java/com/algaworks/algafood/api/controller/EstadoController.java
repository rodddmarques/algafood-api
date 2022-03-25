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

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadosService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadosService estadosService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Estado> listar() {
		return estadoRepository.findAll();
	}
	
	@GetMapping("/{idEstado}")
	public ResponseEntity<Estado> buscar(@PathVariable Long idEstado) {
		
		Optional<Estado> estado = estadoRepository.findById(idEstado);
		
		if (estado.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(estado.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Estado estado) {
		
		try {
			estado = estadosService.salvar(estado);
			return ResponseEntity.status(HttpStatus.CREATED).body(estado);
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	@PutMapping("/{idEstado}")
	public ResponseEntity<?> atualizar(@PathVariable Long idEstado, @RequestBody Estado estado) {
		Optional<Estado> estadoEncontrado = estadoRepository.findById(idEstado);
		
		if (estadoEncontrado.isPresent()) {
			try {
				BeanUtils.copyProperties(estado, estadoEncontrado.get(), "id");
				return ResponseEntity.status(HttpStatus.OK).body(estadosService.salvar(estadoEncontrado.get()));
			} catch (EntidadeEmUsoException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			}
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{idEstado}")
	public ResponseEntity<?> remover(@PathVariable Long idEstado) {
		
		Optional<Estado> estadoEncontrado = estadoRepository.findById(idEstado);
		
		if (estadoEncontrado.isPresent()) {
			try {
				estadosService.deletarEstado(estadoEncontrado.get());
				return ResponseEntity.noContent().build();
			} catch (EntidadeEmUsoException e) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
			}
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
