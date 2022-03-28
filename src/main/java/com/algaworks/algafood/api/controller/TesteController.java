package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@GetMapping("/cozinhas/por-nome")
	public Optional<List<Cozinha>> getCozinhasPorNome(String nome) {
		return cozinhaRepository.findByNomeContaining(nome);
	}
	
	@GetMapping("/cozinhas/unica-por-nome")
	public Optional<Cozinha> getCozinhaPorNome(String nome) {
		return cozinhaRepository.findByNome(nome);
	}
	
	@GetMapping("/restaurantes/buscar-por-taxa-frete")
	public List<Restaurante> getRestaurantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	
	@GetMapping("/restaurantes/existe-por")
	public Boolean existeRestaurantePorNome(String nome) {
		return restauranteRepository.existsByNome(nome);
	}
	
	@GetMapping("restaurantes/primeiro-por-nome")
	public Optional<Restaurante> buscarPrimeiroRestaurantePorNome(String nome) {
		return restauranteRepository.findFirstByNomeContaining(nome);
	}
	
	@GetMapping("restaurantes/nome-e-cozinha")
	public List<Restaurante> buscarRestaurantePorNomeECozinha(String nome, Long cozinha) {
		return restauranteRepository.buscarRestaurantePorNomeECozinha(nome, cozinha);
	}
	
	@GetMapping("/restaurantes/por-nome-taxa-frete")
	public List<Restaurante> getRestaurantesPorNomeETaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.findByCriteria(nome, taxaInicial, taxaFinal);
	}

	/**
	 * Endpoint que usa uma chamada q faz a utilização do padrão specification do DDD.
	 * @param nome
	 * @param taxaInicial
	 * @param taxaFinal
	 * @return
	 */
	@GetMapping("/restaurantes/com-frete-gratis")
	public List<Restaurante> getRestaurantesComTaxaFreteGratis(String nome) {
		
		return restauranteRepository.findComFreteGratis(nome);
	}
	
}
