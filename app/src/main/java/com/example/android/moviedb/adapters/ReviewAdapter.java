package com.example.android.moviedb.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviedb.R;
import com.example.android.moviedb.model.Reviews;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {
    private Context mContext;
    private List<Reviews> mData;

    public ReviewAdapter(Context mContext, List<Reviews> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.reviews, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mAuthor.setText(mData.get(position).getmAuthor());
        holder.mContent.setText(mData.get(position).getmReview());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mAuthor;
        TextView mContent;

        public MyViewHolder(View itemView) {
            super(itemView);

            mAuthor = itemView.findViewById(R.id.tv_author);
            mContent = itemView.findViewById(R.id.tv_review);
        }
    }
}
