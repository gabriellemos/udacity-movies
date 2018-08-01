package com.keg.udacity_movies.moviedb.recycler_view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.keg.udacity_movies.R;
import com.keg.udacity_movies.moviedb.Json2Obj;
import com.keg.udacity_movies.moviedb.RestController;
import com.keg.udacity_movies.moviedb.model.movie.MovieLite;
import com.keg.udacity_movies.moviedb.model.movie.OrderBy;
import com.keg.udacity_movies.moviedb.recycler_view.view_holder.BaseViewHolder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class LoadableAdapter<MovieLite, VH extends BaseViewHolder<MovieLite>> extends SimpleAdapter {

    protected final int VIEW_TYPE_LOADING = 3;
    protected final int VIEW_TYPE_ERROR = 4;

    protected int pageToLoad;
    protected boolean isLoading, isLastPage, hasConnectionError;

    public LoadableAdapter(Context context) {
        this(context, true);
    }

    public LoadableAdapter(Context context, boolean displayEmpty) {
        super(context, displayEmpty);
        pageToLoad = 0;
        isLoading = false;
        isLastPage = false;
        hasConnectionError = false;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        BaseViewHolder<MovieLite> viewHolder;
        if (viewType == VIEW_TYPE_LOADING) {
            view = inflater.inflate(R.layout.loading_card, parent, false);
            viewHolder = new BaseViewHolder(view);
        } else if (viewType == VIEW_TYPE_ERROR) {
            view = inflater.inflate(R.layout.information_card, parent, false);
            viewHolder = new BaseViewHolder(view);
        } else {
            viewHolder = super.onCreateViewHolder(parent, viewType);
        }
        return (VH) viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_EMPTY) {
            TextView textView = (TextView) holder.itemView.findViewById(R.id.card_message);
            textView.setText(R.string.endless_recyclcer_unexpedted_error);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadPage();
                }
            });
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        int itemCount;
        if (isLoading || hasConnectionError) {
            itemCount = getDataSet().size() + 1;
        } else {
            itemCount = super.getItemCount();
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType;
        boolean isLastItem = position + 1 == getItemCount();
        if (isLastItem && isLoading) {
            viewType = VIEW_TYPE_LOADING;
        } else if (isLastItem && hasConnectionError) {
            viewType = VIEW_TYPE_ERROR;
        } else {
            viewType = super.getItemViewType(position);
        }
        return viewType;
    }

    public void loadNextPage() {
        pageToLoad++;
        loadPage();
    }

    protected void loadPage() {
        hasConnectionError = false;
        isLastPage = false;
        isLoading = true;

        RestController.discoverMovie(pageToLoad, OrderBy.POPULARITY_DESC,
                getSuccessListener(), getErrorListener());
    }

    protected Listener<JSONObject> getSuccessListener() {
        return new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    loadSuccess();
                    int page = response.getInt("total_pages");
                    if (pageToLoad == page) isLastPage = true;
                    JSONArray jsArray = response.getJSONArray("results");
                    insertData(Json2Obj.json2MovieLiteList(jsArray));
                } catch (Throwable throwable) {
                    loadFailure();
                    notifyDataSetChanged();
                }
            }
        };
    }

    protected ErrorListener getErrorListener() {
        return new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadFailure();
                notifyDataSetChanged();
            }
        };
    }

    private void loadSuccess() {
        hasConnectionError = false;
        isLastPage = false;
        isLoading = false;
    }

    private void loadFailure() {
        hasConnectionError = true;
        isLastPage = false;
        isLoading = false;
    }

    private void parseData() {

    }

}
