package com.example.android.moviedb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.moviedb.R;
import com.example.android.moviedb.adapters.ReviewAdapter;
import com.example.android.moviedb.model.Reviews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {

    final private String urlStart = "https://api.themoviedb.org/3/movie/";;
    final private String urlReviewEnd = "/reviews?api_key=668f44e5db776bd516c01c02e7eb687c&language=en-US";

    private List<Reviews> reviewList = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviews_activity);

        recyclerView = (RecyclerView) findViewById(R.id.rv_reviews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        final int id = intent.getExtras().getInt("id");
        System.out.println("ID: " + id);

        loadReviews(id);

    }


    private void loadReviews(int id) {
        reviewList.clear();
        String url = urlStart + id + urlReviewEnd;

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("results");
                            if (array.length() == 0){
                                System.out.println("NO REVIEWS");
                                Toast.makeText(getApplicationContext(), "No reviews for this movie.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                for (int i = 0; i < array.length(); i++){
                                    JSONObject o = array.getJSONObject(i);
                                    Reviews reviews = new Reviews(
                                            o.getString("author"),
                                            o.getString("content"));
                                    reviewList.add(reviews);
                                }
                                if (reviewList == null){

                                }
                                adapter = new ReviewAdapter(getApplicationContext(), reviewList);
                                recyclerView.setAdapter(adapter);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("List is null!");
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
