package com.example.mubaloobook.network;

import com.example.mubaloobook.models.MubalooTeam;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * All API requests are defined in the interface. Retrofit builds a rest client from these methods
 * and makes a callback to the user on success/failure.
 */
public interface MubalooApiService {

    @GET("/developerTestResources/team.json")
    public void getMubalooTeam(Callback<List<MubalooTeam>> callback);

}
