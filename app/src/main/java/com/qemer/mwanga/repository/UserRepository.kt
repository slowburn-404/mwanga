package com.qemer.mwanga.repository

import com.qemer.mwanga.api.ApiLoginClient
import com.qemer.mwanga.api.ApiLoginInterface
import com.qemer.mwanga.models.LoginRequest
import com.qemer.mwanga.models.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserRepository {
    val apiClient = ApiLoginClient.buildClient(ApiLoginInterface::class.java)

    //switch the content to the io

    suspend fun login(loginRequest: LoginRequest):
            Response<LoginResponse>{
        return withContext(Dispatchers.IO){
            apiClient.login(loginRequest)
        }
    }
}