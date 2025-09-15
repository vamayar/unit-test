package com.pokemonreview.api.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.pokemonreview.api.models.Pokemon;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)

public class PokemonRepositoryTest {

	@Autowired
	private PokemonRepository pokemonRepository;
	
	@Test
	public void PokemonRepository_SaveAll_ReturnSavedPokemon() {
		
		//Arrange
		
		Pokemon pokemon = Pokemon.builder().name("pikachu").type("electric").build();
		//Act
		
		Pokemon savedPokemon = pokemonRepository.save(pokemon);
		
		//Assert
		Assertions.assertThat(savedPokemon).isNotNull();
		Assertions.assertThat(savedPokemon.getId()).isGreaterThan(0);
	}
	
	@Test
	public void PokemonRepository_GetAll_ReturnsMoreThenOnePokemon() {
		
		//Arrange
		Pokemon pokemon = Pokemon.builder().name("pikachu").type("electric").build();
		Pokemon pokemon2 = Pokemon.builder().name("pikachu").type("electric").build();
		
		//Act
		pokemonRepository.save(pokemon);
		pokemonRepository.save(pokemon2);
		
		//Assert
		List<Pokemon> pokemonList = pokemonRepository.findAll();
		
		Assertions.assertThat(pokemonList).isNotNull();
		Assertions.assertThat(pokemonList.size()).isEqualTo(2);
		
	}
	
	@Test
	public void PokemonRepository_FindById_ReturnPokemon() {
		
		//Arrange
		Pokemon pokemon = Pokemon.builder().name("pikachu").type("electric").build();
		
		//Act
		Pokemon savedPokemon = pokemonRepository.save(pokemon);
		Pokemon pokemonSearched = pokemonRepository.findById(savedPokemon.getId()).get();
	
		
		//Assert
		
		Assertions.assertThat(pokemonSearched.getId()).isEqualTo(savedPokemon.getId());
		
	}
	
	@Test
	public void PokemonRepository_FindByType_ReturnNotNull() {
		//Arrange
		
		Pokemon pokemon = Pokemon.builder().name("pikachu").type("electric").build();
		
		
		//Act
		Pokemon savedPokemon = pokemonRepository.save(pokemon);
		List<Pokemon> returnedPokemon = pokemonRepository.findByType(pokemon.getType());
		
		
		//Assert
		Assertions.assertThat(returnedPokemon).isNotNull();
		Assertions.assertThat(returnedPokemon.size()).isGreaterThan(0);
		
		
	}
	
	@Test
	public void PokemonRepository_UpdatePokemon_ReturnPokemonNotNull() {
		//Arrange
		Pokemon pokemon = Pokemon.builder().name("pikachu").type("electric").build();
		
		//Act
		
		pokemonRepository.save(pokemon);
		
		Pokemon savedPokemon = pokemonRepository.findById(pokemon.getId()).get();
		savedPokemon.setType("Electric");
		savedPokemon.setName("Raichiu");
		
		Pokemon updatedPokemon = pokemonRepository.save(savedPokemon);
		
		//Assert
		Assertions.assertThat(updatedPokemon.getName()).isNotNull();
		Assertions.assertThat(updatedPokemon.getType()).isNotNull();
	}
	
	
	@Test
	public void PokemonRepository_DeletePokemon_ReturnPokemonIsNull() {
		//Arrange
		
		Pokemon pokemon = Pokemon.builder().name("pikachu").type("electric").build();
		
		//Act
		
		pokemonRepository.save(pokemon);
		
		pokemonRepository.deleteById(pokemon.getId());
		
		Optional<Pokemon> returnedPokemon = pokemonRepository.findById(pokemon.getId());
		
		//Assert
		
		Assertions.assertThat(returnedPokemon).isEmpty();
	}
	
}
