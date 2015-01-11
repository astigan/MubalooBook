package com.example.mubaloobook.network;

import retrofit.RestAdapter;

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

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .build();

        apiService = restAdapter.create(MubalooApiService.class);
    }

    public static MubalooApiService get() {
        return apiService;
    }

}
