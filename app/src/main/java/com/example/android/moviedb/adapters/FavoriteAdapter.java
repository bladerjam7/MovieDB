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
import com.example.android.moviedb.database.Favorites;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>{
    private Context mContext;
    private List<Favorites> mDataEntries;

    public FavoriteAdapter(Context mContext) {
        this.mContext = mContext;
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

        Picasso.with(mContext)
                .load(mDataEntries.get(i).getPoster_url())
                .resize(540, 800)
                .into(myViewHolder.mPosters);

        myViewHolder.mPosters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MovieActivity.class);
                intent.putExtra("isFavorite", true);
                intent.putExtra("Id", mDataEntries.get(i).getId());
                intent.putExtra("Title", mDataEntries.get(i).getTitle());
                intent.putExtra("Description", mDataEntries.get(i).getDescription());
                intent.putExtra("Poster", mDataEntries.get(i).getPoster_url());
                intent.putExtra("Rating", mDataEntries.get(i).getRating());
                intent.putExtra("Year", mDataEntries.get(i).getYear());

                mContext.startActivity(intent);


            }
        });

    }

    public void setFavorites(List<Favorites> favoritesEntries){
        mDataEntries = favoritesEntries;
    }

    public List<Favorites> getFavorites(){
        return mDataEntries;
    }

    @Override
    public int getItemCount() {
        if (mDataEntries != null) {
            return mDataEntries.size();
        }else{
            return 0;
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mPosters;


        public MyViewHolder(View itemView) {
            super(itemView);

            mPosters = itemView.findViewById(R.id.im_poster_thumbnail);


        }
    }
}
