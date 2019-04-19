package com.example.android.moviedb.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviedb.R;
import com.example.android.moviedb.model.Trailers;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {
    private Context mContext;
    private List<Trailers> mData;

    public TrailerAdapter(Context context, List<Trailers> data){
        mContext = context;
        mData = data;

    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.trailers, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
        myViewHolder.playButton.setImageResource(R.drawable.play);
        myViewHolder.trailerCount.setText("Trailer " + (i+1));
        myViewHolder.borderline.setImageResource(R.drawable.borderline);

        myViewHolder.trailerCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + mData.get(i).getKey()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + mData.get(i).getKey()));
                try {
                    mContext.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    mContext.startActivity(webIntent);
                }


            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView playButton;
        TextView trailerCount;
        ImageView borderline;


        public MyViewHolder(View itemView) {
            super(itemView);

            playButton = itemView.findViewById(R.id.play_button);
            trailerCount = itemView.findViewById(R.id.trailer_count);
            borderline = itemView.findViewById(R.id.border_line_trailers);


        }
    }
}
