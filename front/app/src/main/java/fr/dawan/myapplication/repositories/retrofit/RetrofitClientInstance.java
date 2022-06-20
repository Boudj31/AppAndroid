package fr.dawan.myapplication.repositories.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    //Lister les URL des API
    public static final String LOCALHOST_BASE_URL = "http://192.168.1.42:8080";    public static final String INSEE_BASE_URL = "INSEE URL";

    private volatile static Retrofit retrofit;

    private RetrofitClientInstance(){

    }

    public static synchronized Retrofit getRetrofitInstance(String baseUrl){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create()) //modifier si autre convertisseur utilisé
                    .build();
        }
        return retrofit;
    }

}
