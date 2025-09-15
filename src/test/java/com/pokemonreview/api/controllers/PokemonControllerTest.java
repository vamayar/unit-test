package com.pokemonreview.api.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.ReviewDto;
import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.models.Review;
import com.pokemonreview.api.service.PokemonService;
import static org.mockito.BDDMockito.given;


@WebMvcTest(controllers = PokemonController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PokemonControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PokemonService pokemonService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Pokemon pokemon;
	private Review review;
	private ReviewDto reviewDto;
	private PokemonDto pokemonDto;
	
	@BeforeEach
	public void init() {
		 pokemon = Pokemon.builder().name("pikachu").type("electric").build();
		 pokemonDto = PokemonDto.builder().name("pikachu").type("electric").build();
		 review = Review.builder().title("title").content("content").stars(5).build();
		 reviewDto = ReviewDto.builder().title("title").content("content").stars(5).build();
	}
	
	@Test
	public void PokemonController_CreatePokemon_ReturnCreated() throws Exception {
		
		given(pokemonService.createPokemon(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));
		
		ResultActions response = mockMvc.perform(
					post("/api/pokemon/create").
					contentType(MediaType.APPLICATION_JSON).
					content(objectMapper.writeValueAsString(pokemonDto))
				);
		
		response.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(pokemonDto.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(pokemonDto.getType())));
	}
}
