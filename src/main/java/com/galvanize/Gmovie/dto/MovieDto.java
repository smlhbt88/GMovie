package com.galvanize.Gmovie.dto;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.List;

@Data
public class MovieDto {
    private String title;
    private String director;
    private List<String> actors=new ArrayList<>();
    private int release;
    private String description;
    private List<Integer> rating=new ArrayList<>();
    private String message;
    private int average;
    private List<String> review=new ArrayList<>();

}
