package com.keg.udacity_movies.moviedb;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.keg.udacity_movies.VolleyRequest;
import com.keg.udacity_movies.moviedb.model.movie.MovieLite;
import com.keg.udacity_movies.moviedb.model.movie.OrderBy;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class Json2ObjTest {

    private static Context appContext;

    @BeforeClass
    public static void beforeClass() {
        appContext = InstrumentationRegistry.getTargetContext();
        VolleyRequest.init(appContext);
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

        while (!request.hasHadResponseDelivered()) {}
    }

}
