package com.example.mubaloobook.network;

import com.google.gson.JsonElement;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * All API requests are defined in the interface. Retrofit builds a rest client from these methods
 * and makes a callback to the user on success/failure.
 */
public interface MubalooApiService {

    @GET("/developerTestResources/team.json")
    public void getMubalooTeam(Callback<List<JsonElement>> callback);

}
