package com.vijayganduri.saltside.rest;


import com.vijayganduri.saltside.model.FeedResponse;
import com.vijayganduri.saltside.model.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by vganduri
 */
public interface RestService {

    @GET("maclir/f715d78b49c3b4b3b77f/raw/8854ab2fe4cbe2a5919cea97d71b714ae5a4838d/items.json")
    Call<List<Item>> getFeed();

}
