package com.qemer.mwanga.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    var message:String,
   @SerializedName("acesss_token") var accessToken:String,
   @SerializedName("user_Id") var userId:String
)
