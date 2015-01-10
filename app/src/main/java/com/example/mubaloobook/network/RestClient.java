package com.example.mubaloobook.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Provides the MubalooApiService as a singleton, making API calls very compact
 * e.g. RestClient.get().fooBar();
 */
public class RestClient {

    private static final String BASE_URL = "http://mubaloo.com/dev";

    private static MubalooApiService apiService;

    static {
        setupRestClient();
    }

    private RestClient() { }

    private static void setupRestClient() {

        // requires custom deserialiser as JSON array objects can be of type Team or Team Member
        Gson gson = new GsonBuilder()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        apiService = restAdapter.create(MubalooApiService.class);
    }

    public static MubalooApiService get() {
        return apiService;
    }

}
