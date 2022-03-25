package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

//@JsonRootName >> Anotação utilizada para substituir no nome da classe na exibiçao dos dados em json.
//@JsonRootName("gastronomia")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cozinha {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@JsonIgnore >> Ignorar a propriedade na exibição do json
	//@JsonProperty >> quando precisa alterar o nome da representação no retorno do json, usa-se a anotação abaixo.
	//@JsonProperty("titulo")	
	@Column(nullable = false)
	private String nome;

}
