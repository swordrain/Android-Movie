package edu.self.movies.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import edu.self.movies.Model.Cast;
import edu.self.movies.Model.Movie;
import edu.self.movies.R;

public class MovieDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SimpleDraweeView imageView;
    private Movie movie;
    private TextView originalTitle;
    private TextView genres;
    private TextView rating;
    private LinearLayout directorsContainer;
    private LinearLayout castsContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movie = (Movie)getIntent().getSerializableExtra("movie");

        initControls();

    }

    private void initControls(){
        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(movie.getCnName());

        imageView = (SimpleDraweeView)findViewById(R.id.detail_image);
        imageView.setImageURI(movie.getImageURL());

        originalTitle = (TextView)findViewById(R.id.detail_original_title);
        originalTitle.setText(movie.getEnName());

        genres = (TextView)findViewById(R.id.detail_genres);
        String genresStr = "";
        for(int i = 0; i < movie.getGenres().length; i++){
            genresStr += movie.getGenres()[i];
            if (i != movie.getGenres().length - 1){
                genresStr += ",";
            }
        }

        genres.setText(genresStr);

        rating = (TextView)findViewById(R.id.detail_rating);
        rating.setText(String.valueOf(movie.getRating()));

        directorsContainer = (LinearLayout)findViewById(R.id.directors_container);

        for(int i = 0; i < movie.getDirectors().size(); i++){
            Cast cast = movie.getDirectors().get(i);
            View view = getLayoutInflater().inflate(R.layout.cast_item, null);
            ((SimpleDraweeView)view.findViewById(R.id.cast_image)).setImageURI(cast.getImageUrl());
            ((TextView)view.findViewById(R.id.cast_name)).setText(cast.getName());
            directorsContainer.addView(view);
        }

        castsContainer = (LinearLayout)findViewById(R.id.casts_container);

        for(int i = 0; i < movie.getCasts().size(); i++){
            Cast cast = movie.getCasts().get(i);
            View view = getLayoutInflater().inflate(R.layout.cast_item, null);
            ((SimpleDraweeView)view.findViewById(R.id.cast_image)).setImageURI(cast.getImageUrl());
            ((TextView)view.findViewById(R.id.cast_name)).setText(cast.getName());
            castsContainer.addView(view);
        }

    }
}
