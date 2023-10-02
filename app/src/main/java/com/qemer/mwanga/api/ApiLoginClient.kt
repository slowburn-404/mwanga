package com.qemer.mwanga.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiLoginClient {
    val retrofit = Retrofit.Builder()
            .baseUrl("https://qemer-backend-764e0de661a5.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        fun <T> buildClient(apiInterface:Class<T>):T{
            return retrofit.create(apiInterface)
        }
}