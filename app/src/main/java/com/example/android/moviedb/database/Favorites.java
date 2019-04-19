package com.example.android.moviedb.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "favorites")
public class Favorites {

    @PrimaryKey(autoGenerate = true)
    private int uniqueId;
    private int id;
    private String title;
    private String poster_url;
    private String description;
    private double rating;
    private String year;
    private boolean isFavorited;

    public Favorites() {
    }

    @Ignore
    public Favorites(int uniqueId, int id, String title, String poster_url, String description, double rating, String year) {
        this.uniqueId = uniqueId;
        this.id = id;
        this.title = title;
        this.poster_url = poster_url;
        this.description = description;
        this.rating = rating;
        this.year = year;
    }

    public Favorites(int id, String title, String poster_url, String description, double rating, String year) {
        this.id = id;
        this.title = title;
        this.poster_url = poster_url;
        this.description = description;
        this.rating = rating;
        this.year = year;
        this.isFavorited = isFavorited;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    public String getYear() {
        return year;
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setFavorited(boolean favorited) {
        isFavorited = favorited;
    }
}
