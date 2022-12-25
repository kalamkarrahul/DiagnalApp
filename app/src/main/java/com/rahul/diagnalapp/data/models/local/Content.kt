package com.rahul.diagnalapp.data.models.local

import com.google.gson.annotations.SerializedName

data class Content(
    val name: String,
    @SerializedName("poster-image")
    val posterImage: String
)
