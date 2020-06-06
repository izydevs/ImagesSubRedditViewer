package com.amit.android.imagesubredditviewer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepository() {

    private var list: MutableLiveData<List<ImageData>> = MutableLiveData()

    fun getUploadedImageListData(): LiveData<List<ImageData>> = list

    fun getImageList() {

        val apiInterface = RESTAPIBuilder.getInstance()
        val call = apiInterface.subRedditData()
        call.enqueue(object : Callback<SubRedditImage> {
            override fun onResponse(
                call: Call<SubRedditImage>,
                response: Response<SubRedditImage>
            ) {
                val listImageData = response.body()!!.data.children
                list.postValue(listImageData)
                Log.d("asdf", "response ${response.body().toString()}")
            }

            override fun onFailure(call: Call<SubRedditImage>, t: Throwable) {
                // the network call was a failure
                Log.d("asdf", "Throwable $t")

            }
        })

    }

}