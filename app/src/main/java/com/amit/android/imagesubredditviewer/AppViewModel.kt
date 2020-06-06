package com.amit.android.imagesubredditviewer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AppRepository()

    fun getImageList() = repository.getImageList()
    fun getImageListData(): LiveData<List<ImageData>> = repository.getUploadedImageListData()
}