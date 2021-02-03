package com.galvanize.Gmovie.controller;

import com.galvanize.Gmovie.dto.MovieDto;
import com.galvanize.Gmovie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gmovie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/movies")
    public List<MovieDto> getAllMovies (){
        return movieService.getAllMovies();
    }

    @GetMapping("/movies/")
    public MovieDto getMovieByTitle(@RequestParam String title){
        return movieService.getMovieByTitle(title);
    }

    @PatchMapping("/movies")
    public MovieDto updateMovieRate(@RequestParam Long id, @RequestParam Integer rate){
        return movieService.updateMovieRate(id,rate);
    }




}
