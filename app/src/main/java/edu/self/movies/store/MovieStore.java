package edu.self.movies.store;

import java.util.ArrayList;
import java.util.List;

import edu.self.movies.Model.Movie;

/**
 * Created by lianli on 2017/3/6.
 */

public class MovieStore {

    private static MovieStore instance = new MovieStore();

    public static MovieStore getInstance(){
        return instance;
    }

    private List<Movie> top250MovieList = new ArrayList<>();
    private List<Movie> searchList = new ArrayList<>();
    private List<Movie> comingMovieList = new ArrayList<>();
    private List<Movie> inTheaterMovieList = new ArrayList<>();
    private int top250Total;
    private int comingTotal;
    private int inTheaterTotal;
    private int searchTotal;

    public int getSearchTotal() {
        return searchTotal;
    }

    public void setSearchTotal(int searchTotal) {
        this.searchTotal = searchTotal;
    }

    public List<Movie> getTop250MovieList() {
        return top250MovieList;
    }

    public void setTop250MovieList(List<Movie> top250MovieList) {
        this.top250MovieList = top250MovieList;
    }

    public int getTop250Total() {
        return top250Total;
    }

    public void setTop250Total(int top250Total) {
        this.top250Total = top250Total;
    }

    public List<Movie> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<Movie> searchList) {
        this.searchList = searchList;
    }

    public List<Movie> getComingMovieList() {
        return comingMovieList;
    }

    public void setComingMovieList(List<Movie> comingMovieList) {
        this.comingMovieList = comingMovieList;
    }

    public List<Movie> getInTheaterMovieList() {
        return inTheaterMovieList;
    }

    public void setInTheaterMovieList(List<Movie> inTheaterMovieList) {
        this.inTheaterMovieList = inTheaterMovieList;
    }

    public int getComingTotal() {
        return comingTotal;
    }

    public void setComingTotal(int comingTotal) {
        this.comingTotal = comingTotal;
    }

    public int getInTheaterTotal() {
        return inTheaterTotal;
    }

    public void setInTheaterTotal(int inTheaterTotal) {
        this.inTheaterTotal = inTheaterTotal;
    }
}
