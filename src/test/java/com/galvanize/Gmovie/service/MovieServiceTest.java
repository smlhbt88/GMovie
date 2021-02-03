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
                "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop",null);

        Movie movie2=new Movie("Superman Returns","Bryan Singer",null,2006,
                "Superman returns to Earth after spending five years in space examining his " +
                        "homeworld Krypton. But he finds things have changed while he was gone, and he must once again prove himself important to the world"
                ,null);



        List<Movie> expected=new ArrayList<>();
        expected.add(movie1);
        expected.add(movie2);

        when(movieRepository.findAll()).thenReturn(expected);


        List<MovieDto> actual=movieService.getAllMovies();

        System.out.println(actual);
        verify(movieRepository, times(1)).findAll();

        assertEquals(expected.size(),actual.size());
    }

}