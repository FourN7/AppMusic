package com.example.appmusic.Serviec;

public class APIService {
    private  static String base_url="https://nhutnam7.000webhostapp.com/Server/";
    public  static  Dataservice getService()
    {
        return APIRetrofitClient.getCLient(base_url).create(Dataservice.class);
    }
}
