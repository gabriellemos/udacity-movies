package com.keg.udacity_movies.moviedb;

import com.keg.udacity_movies.moviedb.model.movie.MovieLite;

import org.json.JSONObject;

public class Json2Obj {

    public static MovieLite json2MovieLite(JSONObject jsonObject) {
        try {
            MovieLite movieLite = new MovieLite();
            movieLite.setId             (jsonObject.getInt("id"));
            movieLite.setPopularity     (jsonObject.getInt("popularity"));
            movieLite.setVoteAverage    (jsonObject.getInt("vote_average"));
            movieLite.setTitle          (jsonObject.getString("title"));
            movieLite.setOverview       (jsonObject.getString("overview"));
            movieLite.setPosterPath     (jsonObject.getString("poster_path"));
            movieLite.setReleaseDate    (jsonObject.getString("release_date"));
            return movieLite;
        } catch (Throwable throwable) {
            return null;
        }
    }

}
