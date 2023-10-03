package com.qemer.mwanga.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message") var message: String,
)
