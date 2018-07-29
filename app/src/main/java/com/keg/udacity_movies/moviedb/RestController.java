package com.keg.udacity_movies.moviedb;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.keg.udacity_movies.VolleyRequest;
import com.keg.udacity_movies.moviedb.model.movie.OrderBy;

import org.json.JSONObject;

import static com.keg.udacity_movies.moviedb.MovieDB.API_KEY;
import static com.keg.udacity_movies.moviedb.MovieDB.BASE_API_URL;

public class RestController {

    public static Request discoverMovie(int page,
                                        OrderBy orderByParameter,
                                        Listener<JSONObject> listener,
                                        ErrorListener errorListener) {

        if (orderByParameter == null) return null;
        StringBuilder stringBuilder = new StringBuilder(BASE_API_URL)
                .append("discover/movie?api_key=").append(API_KEY)
                .append("&sort_by=").append(orderByParameter.getSortParameter())
                .append("&page=").append(page);

        String url = stringBuilder.toString();
        JsonRequest mRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, listener, errorListener);
        return VolleyRequest.getInstance().getmRequestQueue().add(mRequest);
    }

}
