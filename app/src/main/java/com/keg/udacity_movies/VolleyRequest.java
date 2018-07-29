package com.keg.udacity_movies;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

public class VolleyRequest {

    private static VolleyRequest instance;

    public static void init(Context context) {
        if (instance != null) throw new InstanceAlreadyInitializedException();
        else instance = new VolleyRequest(context);
    }

    public static VolleyRequest getInstance() {
        if (instance == null) throw new NoInstanceFoundException();
        else return instance;
    }

    private RequestQueue mRequestQueue;

    private VolleyRequest(Context context) {
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
    }

    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }

    public static class NoInstanceFoundException extends RuntimeException {
        public NoInstanceFoundException() {
            super("No instance found!");
        }
    }

    public static class InstanceAlreadyInitializedException extends RuntimeException {
        public InstanceAlreadyInitializedException() {
            super("Instance already initialized!");
        }
    }

    /**
     * This method should be used for tests, ONLY!
     */
    protected static void disableInstance() {
        if (instance == null) return;
        getInstance().getmRequestQueue().stop();
        instance = null;
    }

}
