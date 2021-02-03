package com.galvanize.Gmovie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.Gmovie.dto.MovieDto;
import com.galvanize.Gmovie.model.Movie;
import com.galvanize.Gmovie.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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

    @BeforeEach
    public void setUp(){
        movieRepository.deleteAll();
    }




    @Test
    public void fetchAllMovies() throws Exception {
        Movie movie1=new Movie("The Avengers","Joss Whedon",null,2012,
                "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop");

        Movie movie2=new Movie("Superman Returns","Bryan Singer",null,2006,
                "Superman returns to Earth after spending five years in space examining his " +
                        "homeworld Krypton. But he finds things have changed while he was gone, and he must once again prove himself important to the world"
                        );

        movieRepository.save(movie1);
        movieRepository.save(movie2);

        String expectedMovies=objectMapper.writeValueAsString(movieRepository.findAll().stream().map(movie -> modelMapper
                .map(movie, MovieDto.class)).collect(Collectors.toList()));


        String actualMovies=mockMvc.perform(get("/gmovie/movies"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(expectedMovies).isEqualTo(actualMovies);


    }



    @Test
    public void searchMovieByTitle() throws Exception {
        Movie movie1=new Movie("The Avengers","Joss Whedon",null,2012,
                "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop");

        Movie movie2=new Movie("Superman Returns","Bryan Singer",null,2006,
                "Superman returns to Earth after spending five years in space examining his " +
                        "homeworld Krypton. But he finds things have changed while he was gone, and he must once again prove himself important to the world"
                );

        movieRepository.save(movie1);
        movieRepository.save(movie2);


        mockMvc.perform(get("/gmovie/movies/?title=The Avengers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("The Avengers"))
                .andExpect(jsonPath("$.director").value("Joss Whedon"))
                .andExpect(jsonPath("$.release").value(2012))
                .andExpect(jsonPath("$.description").value("Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop"));

    }

    @Test
    public void searchByTitle_NoneExtingMovie() throws Exception {
        Movie movie1=new Movie("The Avengers","Joss Whedon",null,2012,
                "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop");

        Movie movie2=new Movie("Superman Returns","Bryan Singer",null,2006,
                "Superman returns to Earth after spending five years in space examining his " +
                        "homeworld Krypton. But he finds things have changed while he was gone, and he must once again prove himself important to the world"
                );

        movieRepository.save(movie1);
        movieRepository.save(movie2);


        mockMvc.perform(get("/gmovie/movies/?title=Titanic"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Movie does not exist!"));
    }



//    Given an existing movie
//    When I submit a 5 star rating
//    Then I can see it in the movie details.
//
//    Given a movie with one 5 star rating and one 3 star rating
//    When I view the movie details
//    Then I expect the star rating to be 4.

    @Test
    public void movieRating() throws Exception {
        Movie movie1=new Movie("The Avengers","Joss Whedon",null,2012,
                "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop");

        Long  id=movieRepository.save(movie1).getId();

        mockMvc.perform(patch("/gmovie/movies/?id="+id+"&rate="+5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating[0]").value(5))
                .andExpect(jsonPath("$.title").value("The Avengers"))
                .andExpect(jsonPath("$.director").value("Joss Whedon"))
                .andExpect(jsonPath("$.release").value(2012))
                .andExpect(jsonPath("$.description").value("Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop"));

    }

    @Test
    public void averageRating() throws Exception {
        Movie movie1=new Movie("The Avengers","Joss Whedon",null,2012,
                "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop");

        movieRepository.save(movie1);

        Long  id=movieRepository.save(movie1).getId();

        mockMvc.perform(patch("/gmovie/movies/?id="+id+"&rate="+5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.average").value(5))
                .andExpect(jsonPath("$.rating[0]").value(5))
                .andExpect(jsonPath("$.title").value("The Avengers"))
                .andExpect(jsonPath("$.director").value("Joss Whedon"))
                .andExpect(jsonPath("$.release").value(2012))
                .andExpect(jsonPath("$.description").value("Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop"));




        mockMvc.perform(patch("/gmovie/movies/?id="+id+"&rate="+3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.average").value(4))
                .andExpect(jsonPath("$.rating[1]").value(3))
                .andExpect(jsonPath("$.title").value("The Avengers"))
                .andExpect(jsonPath("$.director").value("Joss Whedon"))
                .andExpect(jsonPath("$.release").value(2012))
                .andExpect(jsonPath("$.description").value("Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop"));

    }

    @Test
    public void movieReview() throws Exception {
        Movie movie1=new Movie("The Avengers","Joss Whedon",null,2012,
                "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop");

        movieRepository.save(movie1);
        String review="The movie was great";

        Long  id=movieRepository.save(movie1).getId();

        mockMvc.perform(patch("/gmovie/movies/review/?id="+id+"&rate="+5)
        .contentType(MediaType.APPLICATION_JSON)
        .content(review)).andExpect(status().isOk())
                .andExpect(jsonPath("$.average").value(5))
                .andExpect(jsonPath("$.review").value("The movie was great"))
                .andExpect(jsonPath("$.rating[0]").value(5))
                .andExpect(jsonPath("$.title").value("The Avengers"))
                .andExpect(jsonPath("$.director").value("Joss Whedon"))
                .andExpect(jsonPath("$.release").value(2012))
                .andExpect(jsonPath("$.description").value("Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop"));

    }

    @Test
    public void movieReviewWithOutRating() throws Exception {
        Movie movie1=new Movie("The Avengers","Joss Whedon",null,2012,
                "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop");

        movieRepository.save(movie1);
        String review="The movie was great";

        Long  id=movieRepository.save(movie1).getId();

        mockMvc.perform(patch("/gmovie/movies/review/?id="+id+"&rate="+0)
                .contentType(MediaType.APPLICATION_JSON)
                .content(review)).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Rating is required"));
    }
}