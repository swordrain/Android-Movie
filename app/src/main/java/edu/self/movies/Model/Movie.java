package edu.self.movies.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lianli on 2017/3/5.
 */

public class Movie implements Serializable {

    private String imageURL;
    private String cnName;
    private String enName;
    private String year;
    private String[] genres;
    private double rating;
    private List<Cast> casts;
    private List<Cast> directors;
    private String url;


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Cast> getCasts() {
        return casts;
    }

    public void setCasts(List<Cast> casts) {
        this.casts = casts;
    }

    public List<Cast> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Cast> directors) {
        this.directors = directors;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
