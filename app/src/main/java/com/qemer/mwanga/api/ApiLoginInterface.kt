package com.qemer.mwanga.api

import com.qemer.mwanga.models.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiLoginInterface {
    @POST("api/healthworker/login")
    suspend fun <LogInResponse : Any?> login(@Body request: LoginRequest): Response<LogInResponse>
}