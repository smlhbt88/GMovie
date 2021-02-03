package com.galvanize.Gmovie.service;

import com.galvanize.Gmovie.dto.MovieDto;
import com.galvanize.Gmovie.model.Movie;
import com.galvanize.Gmovie.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Autowired
    private ModelMapper modelMapper;



    @Test
    public void getAllMovies(){
        Movie movie1=new Movie("The Avengers","Joss Whedon",null,2012,
                "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop");

        Movie movie2=new Movie("Superman Returns","Bryan Singer",null,2006,
                "Superman returns to Earth after spending five years in space examining his " +
                        "homeworld Krypton. But he finds things have changed while he was gone, and he must once again prove himself important to the world"
                );



        List<Movie> expected=new ArrayList<>();
        expected.add(movie1);
        expected.add(movie2);

        when(movieRepository.findAll()).thenReturn(expected);


        List<MovieDto> actual=movieService.getAllMovies();

        System.out.println(actual);
        verify(movieRepository, times(1)).findAll();

        assertEquals(expected.size(),actual.size());
    }

    @Test
    public void getMovieByTitle(){
        Movie movie1=new Movie("The Avengers","Joss Whedon",null,2012,
                "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop");

       when(movieRepository.findByTitle("The Avengers")).thenReturn(movie1);

       MovieDto actual=movieService.getMovieByTitle("The Avengers");

       verify(movieRepository,times(1)).findByTitle("The Avengers");

       assertEquals(movie1.getTitle(),actual.getTitle());

    }

    @Test
    public void updateMovieRate(){
        Movie movie1=new Movie("The Avengers","Joss Whedon",null,2012,
                "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop");

        movie1.setId(1L);
        when(movieRepository.getOne(movie1.getId())).thenReturn(movie1);
        MovieDto actual=movieService.updateMovieRate(movie1.getId(),4);

        verify(movieRepository, times(1)).getOne(movie1.getId());

        assertEquals(4,actual.getRating().get(0));

    }

    @Test
    public void getMovieDetailsWithReview(){
        Movie movie1=new Movie("The Avengers","Joss Whedon",null,2012,
                "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop");

        String review="The movie was great";
        movie1.setId(1L);
        when(movieRepository.getOne(movie1.getId())).thenReturn(movie1);

        MovieDto actual=movieService.getMovieDetailsWithReview(review,movie1.getId(),5);

        verify(movieRepository,times(1)).getOne(movie1.getId());

        assertEquals(movie1.getReview().get(0),actual.getReview().get(0));
       assertEquals(movie1.getRating().get(0),actual.getRating().get(0));

    }

}