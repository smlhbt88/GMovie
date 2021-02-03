package com.galvanize.Gmovie.service;

import com.galvanize.Gmovie.dto.MovieDto;
import com.galvanize.Gmovie.model.Movie;
import com.galvanize.Gmovie.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
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
            MovieDto movieDto=helper(movie);
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
         movieDto=helper(movie);
        //return modelMapper.map(movieRepository.findByTitle(title), MovieDto.class);
        return movieDto;
    }

    public MovieDto updateMovieRate(Long id, Integer rate) {
        int sum=0;
        Movie movie=movieRepository.getOne(id);
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



    /****************************** helper methods********************************/

    private MovieDto averageCalculator(int sum, Movie movie) {
         MovieDto movieDto=helper(movie);
        for(Integer r: movieDto.getRating()){
            sum+=r;
        }
        movieDto.setAverage(sum/movieDto.getRating().size());
        return movieDto;
    }

    private MovieDto helper(Movie movie) {
        System.out.println("_________"+movie);
        MovieDto movieDto=new MovieDto();
        movieDto.setTitle(movie.getTitle());
        movieDto.setActors(movie.getActors());
        movieDto.setRating(movie.getRating());
        movieDto.setDescription(movie.getDescription());
        movieDto.setDirector(movie.getDirector());
        movieDto.setRelease(movie.getRelease());
        movieDto.setReview(movie.getReview());

        return movieDto;
    }
}
