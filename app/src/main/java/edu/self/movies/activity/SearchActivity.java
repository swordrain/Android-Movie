package edu.self.movies.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.self.movies.Model.Cast;
import edu.self.movies.Model.Movie;
import edu.self.movies.R;
import edu.self.movies.adapter.MovieAdapter;
import edu.self.movies.store.MovieStore;
import edu.self.movies.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SearchView searchView;
    private RecyclerView searchRecyclerView;
    private MovieAdapter adapter;
    private MovieStore movieStore = MovieStore.getInstance();
    private boolean isRefreshing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = (Toolbar) findViewById(R.id.search_toolbar);

        setSupportActionBar(toolbar);

        searchRecyclerView = (RecyclerView)findViewById(R.id.rearch_recycler_view);
        adapter = new MovieAdapter(movieStore.getSearchList());
        searchRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        searchRecyclerView.setLayoutManager(layoutManager);
        searchRecyclerView.setItemAnimator(new DefaultItemAnimator());

        searchRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //屏幕中最后一个可见子项的position
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                //当前屏幕所看到的子项个数
                int visibleItemCount = layoutManager.getChildCount();
                //当前RecyclerView的所有子项个数
                int totalItemCount = layoutManager.getItemCount();
                //RecyclerView的滑动状态
                int state = recyclerView.getScrollState();
                if(visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE){
                    fetchSearchResult(searchView.getQuery().toString());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView)searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieStore.getSearchList().clear();
                movieStore.setSearchTotal(0);
                fetchSearchResult(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    private void fetchSearchResult(String query){
        if(!isRefreshing) {
            final List<Movie> movies = new ArrayList<>();
            if (movieStore.getSearchTotal() == 0 || movieStore.getSearchTotal() != movieStore.getSearchList().size()) {
                isRefreshing = true;
                HttpUtil.sendHttpGetRequest("https://api.douban.com/v2/movie/search?q=" + query + "&start=" + movieStore.getSearchList().size(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        isRefreshing = false;
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        isRefreshing = false;
                        final String responseStr = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject responseJsonObject = new JSONObject(responseStr);
                                    JSONArray movieArray = responseJsonObject.getJSONArray("subjects");
                                    movieStore.setSearchTotal(responseJsonObject.getInt("total"));
                                    for (int i = 0; i < movieArray.length(); i++) {
                                        Movie movie = new Movie();
                                        JSONObject movieObject = movieArray.getJSONObject(i);
                                        movie.setCnName(movieObject.getString("title"));
                                        movie.setEnName(movieObject.getString("original_title"));
                                        movie.setYear(movieObject.getString("year"));
                                        JSONObject imageObject = movieObject.getJSONObject("images");
                                        movie.setImageURL(imageObject.getString("large"));

                                        movie.setRating(movieObject.getJSONObject("rating").getDouble("average"));
                                        movie.setUrl(movieObject.getString("alt"));

                                        JSONArray genresArray = movieObject.getJSONArray("genres");
                                        String[] genres = new String[genresArray.length()];
                                        for(int j = 0; j < genresArray.length(); j++){
                                            genres[j] = genresArray.getString(j);
                                        }
                                        movie.setGenres(genres);

                                        JSONArray castsArray = movieObject.getJSONArray("casts");
                                        List<Cast> casts = new ArrayList<Cast>();
                                        for (int j = 0; j<castsArray.length(); j++){
                                            Cast cast = new Cast();
                                            Object object = castsArray.getJSONObject(j).get("avatars");
                                            if(castsArray.getJSONObject(j).get("avatars") != JSONObject.NULL) {
                                                cast.setImageUrl(castsArray.getJSONObject(j).getJSONObject("avatars").getString("large"));
                                            }
                                            cast.setName(castsArray.getJSONObject(j).getString("name"));
                                            casts.add(cast);
                                        }
                                        movie.setCasts(casts);

                                        JSONArray directorsArray = movieObject.getJSONArray("directors");
                                        List<Cast> directors = new ArrayList<Cast>();
                                        for (int j = 0; j<directorsArray.length(); j++){
                                            Cast cast = new Cast();
                                            if(directorsArray.getJSONObject(j).get("avatars") != JSONObject.NULL) {
                                                cast.setImageUrl(directorsArray.getJSONObject(j).getJSONObject("avatars").getString("large"));
                                            }
                                            cast.setName(directorsArray.getJSONObject(j).getString("name"));
                                            directors.add(cast);
                                        }
                                        movie.setDirectors(directors);

                                        movieStore.getSearchList().add(movie);
                                    }
                                    adapter.notifyDataSetChanged();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
            }
        }
    }
}
