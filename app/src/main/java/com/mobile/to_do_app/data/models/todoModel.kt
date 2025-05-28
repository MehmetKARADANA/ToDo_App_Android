package com.mobile.to_do_app.data.models

import com.google.gson.annotations.SerializedName

data class todoModel(
    @SerializedName("_id") val id: String?,
    @SerializedName("user") val userId: String,
    @SerializedName("text") val text: String,
    val createdAt: String?,
    val updatedAt: String?
)

data class TodoRequest(
    val text: String
)
