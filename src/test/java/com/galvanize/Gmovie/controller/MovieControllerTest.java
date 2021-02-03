package com.galvanize.Gmovie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.Gmovie.dto.MovieDto;
import com.galvanize.Gmovie.model.Movie;
import com.galvanize.Gmovie.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.http.ResponseEntity.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ModelMapper modelMapper;


    private ObjectMapper objectMapper=new ObjectMapper();




    @Test
    public void fetchAllMovies() throws Exception {
        Movie movie1=new Movie("The Avengers","Joss Whedon",null,2012,
                "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop",null);

        Movie movie2=new Movie("Superman Returns","Bryan Singer",null,2006,
                "Superman returns to Earth after spending five years in space examining his " +
                        "homeworld Krypton. But he finds things have changed while he was gone, and he must once again prove himself important to the world"
                        ,null);

        movieRepository.save(movie1);
        movieRepository.save(movie2);

        String expectedMovies=objectMapper.writeValueAsString(movieRepository.findAll().stream().map(movie -> modelMapper
                .map(movie, MovieDto.class)).collect(Collectors.toList()));


        String actualMovies=mockMvc.perform(get("/gmovie/movies"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(expectedMovies).isEqualTo(actualMovies);


    }

}