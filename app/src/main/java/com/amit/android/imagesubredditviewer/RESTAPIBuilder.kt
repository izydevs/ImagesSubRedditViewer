package com.amit.android.imagesubredditviewer


import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/** Manages retrofit api*/
object RESTAPIBuilder {

    lateinit var builder: OkHttpClient

    private fun getOkhttpClient(): OkHttpClient.Builder {
        val okhttpClient = OkHttpClient.Builder()
        okhttpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        okhttpClient.connectTimeout(10, TimeUnit.SECONDS)
        okhttpClient.retryOnConnectionFailure(true)
        return okhttpClient
    }

    /** provides retrofit instance*/
    fun getInstance(): RestApiClient {
        Log.d("asdf","getInstace")
        val retrofit = Retrofit.Builder().baseUrl("https://www.reddit.com/").client(getOkhttpClient().build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofit.create(RestApiClient::class.java)
    }

}