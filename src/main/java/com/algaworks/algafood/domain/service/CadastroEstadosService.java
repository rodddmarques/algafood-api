package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadosService {

	@Autowired
	EstadoRepository estadoRepository;
	
	public Estado salvar(Estado estado) {
		Optional<List<Estado>> estadoEncontrado = estadoRepository.findByNomeOrSigla(estado.getNome(), estado.getSigla());
		
		if (estadoEncontrado.isPresent()) {
			throw new EntidadeEmUsoException("O nome ou sigla do estado informado ja existe na base de dados.");
		}
		
		return estadoRepository.save(estado);
	}

	public void deletarEstado(Estado estado) {
		try {
			estadoRepository.delete(estado);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Estado com código %d não pode ser removido "
							+ "pois existem cidades associadas ao registro!", estado.getId()));
		}
	}
	
	

	
}
