package com.vijayganduri.saltside.rest;

import com.vijayganduri.saltside.Config;
import com.vijayganduri.saltside.model.FeedResponse;
import com.vijayganduri.saltside.model.Item;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vganduri on 04/17/2016.
 */
public class RestHandler {

    private static final String TAG = RestHandler.class.getSimpleName();
    private static RestHandler ourInstance = new RestHandler();

    private RestService service;

    public static RestHandler getInstance() {
        return ourInstance;
    }

    private RestHandler() {
        OkHttpClient client = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RestService.class);
    }

    public void getFeed(OnResponseListener callback) {
        Call<List<Item>> channelCall = service.getFeed();
        channelCall.enqueue(getCallback(callback));
    }

    private Callback<List<Item>> getCallback(final OnResponseListener callback) {
        return new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                List<Item> items = response.body();
                if (items != null && items.size() > 0) {
                    callback.onResponse(items);
                } else {
                    callback.onError("Could not load questions");
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        };
    }

    public interface OnResponseListener {
        void onResponse(List<Item> articles);

        void onError(String error);
    }

}