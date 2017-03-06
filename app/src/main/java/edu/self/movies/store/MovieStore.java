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
    private int top250Total;


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
}
