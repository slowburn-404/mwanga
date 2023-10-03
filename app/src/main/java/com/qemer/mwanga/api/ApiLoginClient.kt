package com.qemer.mwanga.api

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//object ApiLoginClient {
//    val retrofit = Retrofit.Builder()
//            .baseUrl("https://qemer-backend-764e0de661a5.herokuapp.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        fun <T> buildClient(apiInterface:Class<T>):T{
//            return retrofit.create(apiInterface)
//        }
//
//    fun createApiService(): ApiLoginInterface{
//        return buildClient(ApiLoginInterface::class.java)
//    }
//}
class ApiLoginClient {
    private lateinit var apiService: ApiLoginInterface

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    var gson = GsonBuilder().setLenient().create()

    fun getApiService(context: Context): ApiLoginInterface {
        if (!::apiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://qemer-backend-764e0de661a5.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson)).client(
                    OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
                ).build()

            apiService = retrofit.create(ApiLoginInterface::class.java)
        }
        return apiService
    }
}