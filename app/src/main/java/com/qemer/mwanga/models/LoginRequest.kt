package com.qemer.mwanga.models

import com.google.gson.annotations.SerializedName

data class LoginRequest(
   @SerializedName("phone_number") var phoneNumber: String,
   @SerializedName("password") var password:String
)
