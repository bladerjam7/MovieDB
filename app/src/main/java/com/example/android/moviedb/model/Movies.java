package com.example.android.moviedb.model;

public class Movies {

    private int id;
    private String title;
    private String poster_url;
    private String description;
    //private int runtime;
    private double rating;
    private String year;

    public Movies(int id, double rating, String title, String poster_url, String description,  String year) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
        //this.runtime = length;
        this.year = year;
        this.poster_url = poster_url;
    }

    public void setId(int id) { this.id = id; }

    public void setTitle(String name) { this.title = name; }

    public void setDecription(String decription) {
        this.description = description;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    /*public void setLength(int length) {
        this.runtime = length;
    }*/

    public void setYear(String year) {
        this.year = year;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }


    public int getId() { return id; }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    /*public int getRuntime() {
        return runtime;
    }*/

    public String getYear() {
        return year;
    }

    public String getPoster_url() {
        return poster_url;
    }

}
