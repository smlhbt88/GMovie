package com.galvanize.Gmovie.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Movie {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String director;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> actors=new ArrayList<>();
    private int release;
    private String description;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Integer> rating=new ArrayList<>();
    @ElementCollection
    private List<String> review=new ArrayList<>();

    public Movie(String title, String director, List<String> actors, int release, String description, List<Integer> rating) {
        this.title = title;
        this.director = director;
        this.actors = actors;
        this.release = release;
        this.description = description;
        this.rating = rating;
    }

    public Movie() {

    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public List<String> getActors() {
        return actors;
    }

    public int getRelease() {
        return release;
    }

    public String getDescription() {
        return description;
    }

    public List<Integer> getRating() {
        return rating;
    }

    public List<String> getReview() {
        return review;
    }

    public void setReview(List<String> review) {
        this.review = review;
    }
}
