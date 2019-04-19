package com.example.android.moviedb.activities;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.moviedb.R;
import com.example.android.moviedb.adapters.FavoriteAdapter;
import com.example.android.moviedb.adapters.RecyclerViewAdaptor;
import com.example.android.moviedb.adapters.ReviewAdapter;
import com.example.android.moviedb.database.AppDatabase;
import com.example.android.moviedb.database.Favorites;
import com.example.android.moviedb.model.Movies;
import com.example.android.moviedb.viewModels.MainViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private final static String URL_MOST_POPULAR = "https://api.themoviedb.org/3/movie/popular?api_key=668f44e5db776bd516c01c02e7eb687c&language=en-US&page=";
    private final static String URL_MOST_TOP_RATED = "https://api.themoviedb.org/3/movie/top_rated?api_key=668f44e5db776bd516c01c02e7eb687c&language=en-US&page=";
    private final static String MENU_SELECTED = "selected";
    private final static int favoriteMenu = 2131230789;
    private final static List<Movies> moviesList = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerViewAdaptor adapter;
    private FavoriteAdapter fAdapter;
    private int selected = -1;
    private AppDatabase mDb;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((android.support.v7.widget.Toolbar) findViewById(R.id.toolbar));

        recyclerView = findViewById(R.id.rv_movies_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, calculateNoOfColumns(this)));

        setupAdapter();
        if(savedInstanceState != null){
            selected = savedInstanceState.getInt(MENU_SELECTED);
        } else {
            loadRecyclerViewData(URL_MOST_POPULAR);
        }

        //Loading Database
        UpdateViewModelFavorites();

    }


    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }

    private void setupAdapter() {
        adapter = new RecyclerViewAdaptor(this, moviesList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        if(selected == -1){
            return true;
        }

        switch (selected){
            case R.id.most_popular:
                moviesList.clear();
                loadRecyclerViewData(URL_MOST_POPULAR);
                return true;

            case R.id.favorites:
                moviesList.clear();
                loadFavoritesData();
                return true;

            case R.id.top_rated:
                moviesList.clear();
                loadRecyclerViewData(URL_MOST_TOP_RATED);
                return true;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.most_popular){
            moviesList.clear();
            selected = item.getItemId();
            loadRecyclerViewData(URL_MOST_POPULAR);
        }

        if(item.getItemId() == R.id.top_rated){
            moviesList.clear();
            selected = item.getItemId();
            loadRecyclerViewData(URL_MOST_TOP_RATED);
        }

        if(item.getItemId() == R.id.favorites){
            selected = item.getItemId();
            loadFavoritesData();
        }
        return super.onOptionsItemSelected(item);
    }

    private void UpdateViewModelFavorites(){
        fAdapter = new FavoriteAdapter(getApplicationContext());
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFavorites().observe(this, new Observer<List<Favorites>>() {
            @Override
            public void onChanged(@Nullable List<Favorites> favorites) {
                fAdapter.setFavorites(favorites);
                if (selected == favoriteMenu){
                    loadFavoritesData();
                }
            }
        });
    }

    private void loadRecyclerViewData(final String url) {
        if (recyclerView.getAdapter() == fAdapter){
            recyclerView.setAdapter(adapter);
        }

        final StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONREQUEST(response);
                        adapter.swapData(moviesList);
                    }
                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        // Execute request
        executeRequest(stringRequest);
    }

    private void executeRequest(StringRequest stringRequest) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest); // Adding the stringRequest above to the Queue
    }

    public void JSONREQUEST(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray array = jsonObject.getJSONArray("results");

            for (int i = 0; i < array.length(); i++){
                JSONObject o = array.getJSONObject(i);
                if(o.getString("poster_path") != "null") {
                    Movies movies = new Movies(
                            o.getInt("id"),
                            o.getDouble("vote_average"),
                            o.getString("title"),
                            o.getString("poster_path"),
                            o.getString("overview"),
                            o.getString("release_date")
                    );
                    moviesList.add(movies);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void loadFavoritesData(){
        recyclerView.setAdapter(fAdapter);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MENU_SELECTED, selected);
    }
}
