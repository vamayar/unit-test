package com.pokemonreview.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pokemonreview.api.models.Pokemon;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
    List<Pokemon> findByType(String type);
}
