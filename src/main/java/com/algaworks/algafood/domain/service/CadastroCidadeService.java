package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.DadosInformadosJaExistenteException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Cidade salvar(Cidade cidade) {
	
		Optional<Estado> estadoEncontrado = estadoRepository.findById(cidade.getEstado().getId());
		
		if (estadoEncontrado.isEmpty()) {
			throw new EntidadeNaoEncontradaException(
					String.format("Estado de código %d informado não existe em nossa base de dados", cidade.getEstado().getId()));
		}
			
		cidade.setEstado(estadoEncontrado.get());
		Optional<Cidade> cidadeEncontrada = cidadeRepository.findByNomeAndEstado(cidade.getNome(), cidade.getEstado());

		if (cidadeEncontrada.isPresent()) {
			throw new DadosInformadosJaExistenteException(
					String.format("Cidade informada já existe cadastrada para o estado %s.", cidade.getEstado().getNome()));
		}
		
		return cidadeRepository.save(cidade);
	}

	public void deletarCidade(Cidade cidade) {
		cidadeRepository.delete(cidade);
	}
}
