package com.galvanize.Gmovie.service;

import com.galvanize.Gmovie.dto.MovieDto;
import com.galvanize.Gmovie.model.Movie;
import com.galvanize.Gmovie.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<Movie> movies=movieRepository.findAll();
        List<MovieDto> movieDtos=new ArrayList<>();

        //return movies.stream().map(movie -> modelMapper.map(movie, MovieDto.class)).collect(Collectors.toList());
        for(Movie movie:movies){
            MovieDto movieDto=new MovieDto();
            movieDto.setTitle(movie.getTitle());
            movieDto.setActors(movie.getActors());
            movieDto.setRating(movie.getRating());
            movieDto.setDescription(movie.getDescription());
            movieDto.setDirector(movie.getDirector());
            movieDto.setRelease(movie.getRelease());

            movieDtos.add(movieDto);

        }
        return movieDtos;
    }

    public MovieDto getMovieByTitle(String title) {
        MovieDto movieDto=new MovieDto();
        Movie movie=movieRepository.findByTitle(title);
        if(movie==null){
            movieDto.setMessage("Movie does not exist!");
            return movieDto;
        }

        movieDto.setTitle(movie.getTitle());
        movieDto.setActors(movie.getActors());
        movieDto.setRating(movie.getRating());
        movieDto.setDescription(movie.getDescription());
        movieDto.setDirector(movie.getDirector());
        movieDto.setRelease(movie.getRelease());

        //return modelMapper.map(movieRepository.findByTitle(title), MovieDto.class);

        return movieDto;
    }

    public MovieDto updateMovieRate(Long id, Integer rate) {
        Movie movie=movieRepository.getOne(id);
        int sum=0;

        movie.getRating().add(rate);
        return averageCalculator(sum, movie);
    }

    public MovieDto getMovieDetailsWithReview(String review, Long id, Integer rate) {
        if(rate==0){
            MovieDto movieDto=new MovieDto();
            movieDto.setMessage("Rating is required");
            return movieDto;
        }

        int sum=0;
        Movie movie=movieRepository.getOne(id);

        movie.getRating().add(rate);
        movie.getReview().add(review);
        return averageCalculator(sum, movie);
    }

    private MovieDto averageCalculator(int sum, Movie movie) {
        Movie updatedMovie=movieRepository.save(movie);
        MovieDto movieDto= modelMapper.map(updatedMovie,MovieDto.class);
        for(Integer r: updatedMovie.getRating()){
            sum+=r;
        }
        movieDto.setAverage(sum/updatedMovie.getRating().size());

        return movieDto;
    }
}
