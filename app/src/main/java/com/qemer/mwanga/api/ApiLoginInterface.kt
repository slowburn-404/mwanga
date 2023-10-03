package com.qemer.mwanga.api

import com.qemer.mwanga.models.ChildCreateRequest
import com.qemer.mwanga.models.ChildCreateResponse
import com.qemer.mwanga.models.GetGuardiansResponse
import com.qemer.mwanga.models.GuardianRegistrationRequest
import com.qemer.mwanga.models.GuardianRegistrationResponse
import com.qemer.mwanga.models.LoginRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiLoginInterface {
    @POST("api/healthworker/login")
    suspend fun <LogInResponse : Any?> login(@Body request: LoginRequest): Response<LogInResponse>

    @POST("api/guardians/")
    fun guardianRegistration(@Body guardianRegistrationRequest: GuardianRegistrationRequest): Call<GuardianRegistrationResponse>

    @POST("api/children/")
    fun childRegistration(@Body childCreateRequest: ChildCreateRequest): Call<ChildCreateResponse>

    @GET("api/guardians/")
    fun getGuardians(): Call<ArrayList<GetGuardiansResponse>>

    @GET("api/guardians/{id}")
    fun getGuardians(@Path("id") id: String?): Call<GetGuardiansResponse>
}
