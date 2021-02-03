package com.galvanize.Gmovie.controller;

import com.galvanize.Gmovie.dto.MovieDto;
import com.galvanize.Gmovie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
