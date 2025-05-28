package com.mobile.to_do_app.data.models

import com.google.gson.annotations.SerializedName


data class userModel (
    @SerializedName("_id") val id: String?,
    val name : String,
    val email : String,
    val password: String?,
    val createdAt: String?,
    val updatedAt: String?

)