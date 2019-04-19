package com.example.android.moviedb.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.moviedb.R;
import com.example.android.moviedb.Utils.AppExecutors;
import com.example.android.moviedb.adapters.FavoriteAdapter;
import com.example.android.moviedb.adapters.TrailerAdapter;
import com.example.android.moviedb.database.AppDatabase;
import com.example.android.moviedb.database.Favorites;
import com.example.android.moviedb.model.Reviews;
import com.example.android.moviedb.model.Trailers;
import com.example.android.moviedb.viewModels.MainViewModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity{

    private TextView tvTitle, tvDescription, tvRating, tvRuntime, tvYear, tvReviews;
    private ImageView imPoster;
    private Button mFavButton, mRevButton;

    private String urlStart = "https://api.themoviedb.org/3/movie/";
    private String urlTrailerEnd = "/videos?api_key=668f44e5db776bd516c01c02e7eb687c&language=en-US";
    private String urlReviewEnd = "/reviews?api_key=668f44e5db776bd516c01c02e7eb687c&language=en-US";


    private static final List<Reviews> reviewList = new ArrayList<>();
    private static final List<Trailers> trailerList = new ArrayList<>();
    RecyclerView recyclerViewTrailers;
    RecyclerView.Adapter adapter;
    FavoriteAdapter fadapter;

    private AppDatabase mDb;
    private String string;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_activity);

        mDb = AppDatabase.getInstance(getApplicationContext());

        //Receive Data
        Intent intent = getIntent();
        // boolean isFavorite = intent.getExtras().getBoolean("isFavorite");
        final int uniqueId = intent.getExtras().getInt("uniqueID");
        final int id = intent.getExtras().getInt("Id");
        final String Title = intent.getExtras().getString("Title");
        final String Description = intent.getExtras().getString("Description");
        final String Poster = intent.getExtras().getString("Poster");
        final double Rating = intent.getExtras().getDouble("Rating");
        final String Year = intent.getExtras().getString("Year");


        mDb = AppDatabase.getInstance(getApplicationContext());

        tvTitle = (TextView) findViewById(R.id.tv_title_movie);
        tvDescription = (TextView) findViewById(R.id.tv_description_movie);
        tvRating = (TextView) findViewById(R.id.tv_rate_movie);
        tvYear = (TextView) findViewById(R.id.tv_year_movie);
        imPoster = (ImageView) findViewById(R.id.im_poster);


        mFavButton = (Button) findViewById(R.id.button_favorite);
        mRevButton = (Button) findViewById(R.id.button_reviews);


        tvTitle.setText(Title);
        tvDescription.setText(Description);
        tvRating.setText(String.valueOf(Rating + "/10"));
        tvYear.setText(String.valueOf(Year));
        Picasso.with(this)
                .load(Poster)
                .resize(500,600)
                .centerInside()
                .into(imPoster);



        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFavorites().observe(this, new Observer<List<Favorites>>() {
            @Override
            public void onChanged(@Nullable List<Favorites> favorites) {
                favoriteHandler(id, Title, Description, Poster, Rating, Year, favorites);
            }
        });




        mRevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);




            }
        });

        loadTrailer(id);
        recyclerViewTrailers = findViewById(R.id.rv_trailers);
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));




    }

    private void favoriteHandler(final int id, final String title, final String description, final String poster, final double rating, final String year, final List<Favorites> favorites) {
        boolean isFavorite = checkIfFavorite(favorites, id);
        if (isFavorite){
            mFavButton.setText("Unmark as \nfavorite");
            mFavButton.setBackgroundColor(Color.RED);
            mFavButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onUnFavoriteButtonClicked(favorites, id);
                    Toast.makeText(getApplicationContext(), "Movie is removed from Favorites", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            mFavButton.setText("Mark as \nfavorite");
            mFavButton.setBackgroundColor(Color.GREEN);
            mFavButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onFavoriteButtonClicked(id, title, description, poster, rating, year);
                    Toast.makeText(getApplicationContext(), "Movie is saved to Favorites!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean checkIfFavorite(List<Favorites> favorites, int id) {
        boolean checkFav = false;
        for (int i = 0; i < favorites.size(); i++){
            if (id == favorites.get(i).getId()){
                checkFav = true;
            }
        }
        return checkFav;
    }


    public void onFavoriteButtonClicked(int id, String title, String description, String poster,
                                        double rating, String year){
        final Favorites favorites = new Favorites(id, title, poster, description, rating, year);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.taskDao().insertFavorites(favorites);
            }

        });

    }

    public void onUnFavoriteButtonClicked(final List<Favorites> favorites, final int id){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < favorites.size(); i++){
                    if (id == favorites.get(i).getId()){
                        mDb.taskDao().deleteFavorites(favorites.get(i));
                    }
                }
            }
        });

    }



    private void loadTrailer(int id) {
        trailerList.clear();
        String url = urlStart + id + urlTrailerEnd;

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("results");

                            for (int i = 0; i < array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                Trailers trailers = new Trailers(o.getString("key"));
                                trailerList.add(trailers);
                            }

                            adapter = new TrailerAdapter(getApplicationContext(), trailerList);
                            recyclerViewTrailers.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        // Execute request
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest); // Adding the stringRequest above to the Queue
    }
}
