package com.infomatics.oxfam.twat.repository;

import android.util.Log;

import com.infomatics.oxfam.twat.util.AppConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//TODO
//provision for unregistered bands.
//        Take RFID and keep it.
//
//
//        Settings for previous backup comparison

public class RetrofitService {
    private static Retrofit retrofit;

    public static Retrofit buildRetrofitClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Accept", "application/json");
            builder.addHeader("Content-Type", "application/json");
            //builder.addHeader("checkpoint", Integer.toString(AppConstants.CHECKPOINT_ID));
            Request request = builder.build();
            if(request.body() != null)
                Log.e(AppConstants.TAG,"REQ: "+request.body().toString());
            Log.e(AppConstants.TAG,"PATH: "+request.url().toString());
            Response response = chain.proceed(request);
            String rawJson = response.body().string();
            Log.e(AppConstants.TAG,"RES: "+rawJson);
            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), rawJson)).build();
        });

        httpClient.readTimeout(3, TimeUnit.MINUTES);
        httpClient.connectTimeout(2, TimeUnit.MINUTES);

        retrofit =new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static <S> S cteateService(Class<S> serviceClass) {
        return buildRetrofitClient().create(serviceClass);
    }
}
