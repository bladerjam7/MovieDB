package com.example.android.moviedb.model;

public class Reviews {
    private String mAuthor;
    private String mReview;

    public Reviews(String mAuthor, String mContent) {
        this.mAuthor = mAuthor;
        this.mReview = mContent;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public void setmReview(String mContent) {
        this.mReview = mContent;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmReview() {
        return mReview;
    }
}
