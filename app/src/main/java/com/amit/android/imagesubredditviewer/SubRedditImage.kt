package com.amit.android.imagesubredditviewer

data class SubRedditImage(
    val kind: String,
    val data: ImageDataList
)

data class ImageDataList(
    val modhash: String,
    val dist: Int,
    val children: ArrayList<ImageData>,
    val after: String,
    val before: String
)

data class ImageData(
    val kind: String,
    val data: ImageDataLink
)

data class ImageDataLink(
    val thumbnail: String,
    val url: String
)