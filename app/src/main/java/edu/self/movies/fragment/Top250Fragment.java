package edu.self.movies.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.self.movies.Model.Movie;
import edu.self.movies.R;
import edu.self.movies.adapter.MovieAdapter;
import edu.self.movies.store.MovieStore;
import edu.self.movies.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Top250Fragment extends Fragment {

    private SwipeRefreshLayout top250SwipeRefreshLayout;
    private RecyclerView top250RecyclerView;
    private MovieAdapter adapter;
    private View view;
    private MovieStore movieStore = MovieStore.getInstance();
    private boolean isRefreshing = false;

    public Top250Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_top250, container, false);
        initControls();
        return view;
    }

    private void initControls(){
        top250RecyclerView = (RecyclerView)view.findViewById(R.id.top250_recycler_view);
        top250SwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.top250_swipe_refresh);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        if(movieStore.getTop250MovieList().size() == 0){
            fetchData();
        }

        adapter = new MovieAdapter(movieStore.getTop250MovieList());
        top250RecyclerView.setAdapter(adapter);
        top250RecyclerView.setLayoutManager(layoutManager);
        top250RecyclerView.setItemAnimator(new DefaultItemAnimator());



        top250SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                movieStore.setTop250Total(0);
                movieStore.getTop250MovieList().clear();
                fetchData();
            }
        });


        top250RecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    fetchData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });


    }

    private void fetchData(){
        if(!isRefreshing) {
            final List<Movie> movies = new ArrayList<>();
            if (movieStore.getTop250Total() == 0 || movieStore.getTop250Total() != movieStore.getTop250MovieList().size()) {
                isRefreshing = true;
                top250SwipeRefreshLayout.setRefreshing(true);
                HttpUtil.sendHttpGetRequest("https://api.douban.com/v2/movie/top250?start=" + movieStore.getTop250MovieList().size(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        top250SwipeRefreshLayout.setRefreshing(false);
                        isRefreshing = false;
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        isRefreshing = false;
                        final String responseStr = response.body().string();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                top250SwipeRefreshLayout.setRefreshing(false);
                                try {
                                    JSONObject responseJsonObject = new JSONObject(responseStr);
                                    JSONArray movieArray = responseJsonObject.getJSONArray("subjects");
                                    movieStore.setTop250Total(responseJsonObject.getInt("total"));
                                    for (int i = 0; i < movieArray.length(); i++) {
                                        Movie movie = new Movie();
                                        JSONObject movieObject = movieArray.getJSONObject(i);
                                        movie.setCnName(movieObject.getString("title"));
                                        movie.setEnName(movieObject.getString("original_title"));
                                        movie.setYear(movieObject.getString("year"));
                                        JSONObject imageObject = movieObject.getJSONObject("images");
                                        movie.setImageURL(imageObject.getString("small"));
                                        movieStore.getTop250MovieList().add(movie);
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
