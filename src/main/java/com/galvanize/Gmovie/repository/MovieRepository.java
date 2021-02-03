package com.galvanize.Gmovie.repository;

import com.galvanize.Gmovie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    Movie findByTitle(String title);
}
