package com.keg.udacity_movies.moviedb;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.keg.udacity_movies.VolleyRequest;
import com.keg.udacity_movies.moviedb.model.movie.OrderBy;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RestControllerTest {

    private Context appContext;

    @Before
    public void before() {
        try {
            appContext = InstrumentationRegistry.getTargetContext();
            VolleyRequest.init(appContext);
        } catch (VolleyRequest.InstanceAlreadyInitializedException ignored) {
        }
    }

    @Test
    public void discoverMovie_positiveResponse_test() {
        Request request = RestController.discoverMovie(1, OrderBy.POPULARITY_ASC
                , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Assert.assertTrue(response.has("page"));
                        Assert.assertTrue(response.has("total_results"));
                        Assert.assertTrue(response.has("total_pages"));
                        Assert.assertTrue(response.has("results"));
                        System.out.print(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Assert.fail();
                    }
                });

        while (!request.hasHadResponseDelivered()) {}
    }

}
