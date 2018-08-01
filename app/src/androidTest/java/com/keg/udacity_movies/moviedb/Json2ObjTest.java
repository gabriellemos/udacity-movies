package com.keg.udacity_movies.moviedb;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.keg.udacity_movies.VolleyRequest;
import com.keg.udacity_movies.moviedb.model.movie.Movie;
import com.keg.udacity_movies.moviedb.model.movie.MovieLite;
import com.keg.udacity_movies.moviedb.model.movie.OrderBy;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class Json2ObjTest {

    private static Context appContext;

    @BeforeClass
    public static void beforeClass() {
        appContext = InstrumentationRegistry.getTargetContext();
        VolleyRequest.init(appContext);
    }

    @Test
    public void json2Movie_test() {
        Request request = RestController.movieInformation(353081,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Movie movie = Json2Obj.json2Movie(response);

                            Assert.assertNotNull(movie);
                            Assert.assertNotEquals(0, movie.getId());
                            Assert.assertNotEquals(0, movie.getPopularity());
                            Assert.assertNotEquals(0, movie.getVoteAverage());
                            Assert.assertNotEquals("", movie.getTitle());
                            Assert.assertNotEquals("", movie.getOriginalTitle());
                            Assert.assertNotEquals("", movie.getTagline());
                            Assert.assertNotEquals("", movie.getStatus());
                            Assert.assertNotEquals("", movie.getOverview());
                            Assert.assertNotEquals("", movie.getPosterPath());
                            Assert.assertNotEquals("", movie.getReleaseDate());
                        } catch (Throwable throwable) {
                            Assert.fail();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Assert.fail();
                    }
                });

        while (!request.hasHadResponseDelivered()) {
        }
    }

    @Test
    public void json2MovieLite_test() {
        Request request = RestController.discoverMovie(1, OrderBy.POPULARITY_DESC,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONArray("results").getJSONObject(0);
                            MovieLite movieLite = Json2Obj.json2MovieLite(jsonObject);

                            Assert.assertNotNull(movieLite);
                            Assert.assertNotEquals(0, movieLite.getId());
                            Assert.assertNotEquals(0, movieLite.getPopularity());
                            Assert.assertNotEquals(0, movieLite.getVoteAverage());
                            Assert.assertNotEquals("", movieLite.getTitle());
                            Assert.assertNotEquals("", movieLite.getOverview());
                            Assert.assertNotEquals("", movieLite.getReleaseDate());
                            Assert.assertNotEquals("", movieLite.getPosterPath());
                        } catch (Throwable throwable) {
                            Assert.fail();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Assert.fail();
                    }
                });

        while (!request.hasHadResponseDelivered()) {
        }
    }

    @Test
    public void json2MovieLiteList_test() {
        Request request = RestController.discoverMovie(1, OrderBy.POPULARITY_DESC,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            List<MovieLite> movieLite = Json2Obj.json2MovieLiteList(jsonArray);

                            Assert.assertNotNull(movieLite);
                            Assert.assertTrue(movieLite.size() > 0);
                        } catch (Throwable throwable) {
                            Assert.fail();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Assert.fail();
                    }
                });

        while (!request.hasHadResponseDelivered()) {
        }
    }

}
