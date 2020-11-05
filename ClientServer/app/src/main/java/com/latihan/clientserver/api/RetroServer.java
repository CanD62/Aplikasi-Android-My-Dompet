package com.latihan.clientserver.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rohmad on 12-Sep-19.
 */

public class RetroServer {
    public static  final String base_url = "http://10.0.2.2/uas_android/";

    //akhir upload gambar

    private static Retrofit retrofit;


    public static Retrofit getClient()
    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory( GsonConverterFactory.create())
                    .build();
        }

        return  retrofit;
    }
}
