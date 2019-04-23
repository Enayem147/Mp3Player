package com.example.windows10.mp3player.service;

public class APIService {
    private static String baseUrl = "http://namhnt0508.5gbfree.com/Server/";
    public static DataService getService(){
        return APIRetrofitClient.getClient(baseUrl).create(DataService.class);
    }
}
