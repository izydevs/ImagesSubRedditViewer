package com.amit.android.imagesubredditviewer

import retrofit2.Call
import retrofit2.http.GET


interface RestApiClient {

    @GET("/r/images/hot.json")
    fun subRedditData(): Call<SubRedditImage>
}