package edu.self.movies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import edu.self.movies.Model.Movie;
import edu.self.movies.R;
import edu.self.movies.holder.MovieHolder;

/**
 * Created by lianli on 2017/3/5.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {

    private Context context;
    private List<Movie> movieList;

    public MovieAdapter(List<Movie> movieList){
        this.movieList = movieList;
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.getCnNameTextView().setText(movie.getCnName());
        holder.getEnNameTextView().setText(movie.getEnName());
        holder.getYearTextView().setText(movie.getYear());
        holder.getImageView().setImageURI(movie.getImageURL());
        holder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);

        return new MovieHolder(view);
    }
}
