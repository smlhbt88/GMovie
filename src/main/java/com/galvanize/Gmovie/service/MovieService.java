package com.galvanize.Gmovie.service;

import com.galvanize.Gmovie.dto.MovieDto;
import com.galvanize.Gmovie.model.Movie;
import com.galvanize.Gmovie.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ModelMapper modelMapper;
    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream().map(movie -> modelMapper
                .map(movie,MovieDto.class)).collect(Collectors.toList());
    }

    public MovieDto getMovieByTitle(String title) {
        if(movieRepository.findByTitle(title)==null){
            MovieDto movieDto=new MovieDto();
            movieDto.setMessage("Movie does not exist!");
            return movieDto;
        }

        return modelMapper.map(movieRepository.findByTitle(title), MovieDto.class);
    }

    public MovieDto updateMovieRate(Long id, Integer rate) {
        Movie movie=movieRepository.getOne(id);
        int sum=0;

        movie.getRating().add(rate);
        Movie updatedMovie=movieRepository.save(movie);
       MovieDto movieDto= modelMapper.map(updatedMovie,MovieDto.class);
        for(Integer r: updatedMovie.getRating()){
            sum+=r;
        }
        movieDto.setAverage(sum/updatedMovie.getRating().size());
        return movieDto;
    }
}
