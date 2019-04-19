package com.example.android.moviedb.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.moviedb.R;
import com.example.android.moviedb.activities.MovieActivity;
import com.example.android.moviedb.model.Movies;
import com.example.android.moviedb.viewModels.MovieViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerViewAdaptor.MyViewHolder> {
    private Context mContext;
    private List<Movies> mData;
    private String BASE_IMG_URL = "http://image.tmdb.org/t/p/w185";

    public boolean swapData(List<Movies> movies){
        mData = movies;
        notifyDataSetChanged();
        return true;
    }

    public RecyclerViewAdaptor() {
    }

    public RecyclerViewAdaptor(Context mContext, List<Movies> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.movie_list, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {

        final String img_url = BASE_IMG_URL + mData.get(i).getPoster_url();

        Picasso.with(mContext)
                .load(img_url)
                .resize(540, 800)
                .into(myViewHolder.mPosters);

        myViewHolder.mPosters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(mContext, MovieActivity.class);
                intent.putExtra("favoriteClass", mData.get(i).getClass());
                mContext.startActivity(intent);*/


                Intent intent = new Intent(mContext, MovieActivity.class);
                intent.putExtra("uniqueId", i);
                intent.putExtra("isFavorite", false);
                intent.putExtra("Id", mData.get(i).getId());
                intent.putExtra("Title", mData.get(i).getTitle());
                intent.putExtra("Description", mData.get(i).getDescription());
                intent.putExtra("Poster", img_url);
                intent.putExtra("Rating", mData.get(i).getRating());
                intent.putExtra("Year", mData.get(i).getYear());

                mContext.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mPosters;


        public MyViewHolder(View itemView) {
            super(itemView);

            mPosters = itemView.findViewById(R.id.im_poster_thumbnail);

        }
    }


}


