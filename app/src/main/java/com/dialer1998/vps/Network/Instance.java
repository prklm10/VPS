package com.dialer1998.vps.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Instance {

    private static Retrofit retrofit;

    private  static final String BASE_URL="https://sih2020vps.herokuapp.com/api/";
//    private  static final String BASE_URL="http://192.168.43.63:8000/api/";
//    private  static final String BASE_URL="http://192.168.0.109:8000/api/";

    public static Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory( GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
