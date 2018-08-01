package com.keg.udacity_movies;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class VolleyRequestTest {

    private Context appContext;

    @Before
    public void before() {
        appContext = InstrumentationRegistry.getTargetContext();
        VolleyRequest.disableInstance();
    }

    @Test
    public void init_test() {
        VolleyRequest.init(appContext);
        Assert.assertNotNull(VolleyRequest.getInstance());
    }

    @Test(expected = VolleyRequest.InstanceAlreadyInitializedException.class)
    public void instanceAlreadyCreated_test() {
        VolleyRequest.init(appContext);
        VolleyRequest.init(appContext);
    }

    @Test(expected = VolleyRequest.NoInstanceFoundException.class)
    public void instanceNotFount_test() {
        VolleyRequest.getInstance();
    }

}
