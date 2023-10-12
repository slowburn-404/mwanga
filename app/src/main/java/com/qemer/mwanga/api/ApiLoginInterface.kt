package com.qemer.mwanga.api

import com.qemer.mwanga.models.ChildCreateRequest
import com.qemer.mwanga.models.ChildCreateResponse
import com.qemer.mwanga.models.GetChildrenResponse
import com.qemer.mwanga.models.GetGuardianDetails
import com.qemer.mwanga.models.GetGuardiansResponse
import com.qemer.mwanga.models.GuardianRegistrationRequest
import com.qemer.mwanga.models.GuardianRegistrationResponse
import com.qemer.mwanga.models.LoginRequest
import com.qemer.mwanga.models.LoginResponse
import com.qemer.mwanga.models.SelfCareRequest
import com.qemer.mwanga.models.SelfaCareResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiLoginInterface {
    @POST("api/healthworker/login/")
    fun login(@Body userLogin: LoginRequest): Call<LoginResponse>

    @POST("api/guardians/")
    fun guardianRegistration(@Body guardianRegistrationRequest: GuardianRegistrationRequest): Call<GuardianRegistrationResponse>

    @POST("api/children/")
    fun childRegistration(@Body childCreateRequest: ChildCreateRequest): Call<ChildCreateResponse>

    @GET("api/guardians/")
    fun getGuardians(): Call<ArrayList<GetGuardiansResponse>>

    @GET("api/children/")
    fun getChildren(): Call<ArrayList<GetChildrenResponse>>

    @GET("api/guardians/{id}/")
    fun getGuardianDetails(@Path("id") id: String?): Call<GetGuardianDetails>
}
interface ApiService {

    @POST
    fun postSelfCareData(
        @Url url: String,
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body request: SelfCareRequest
    ): Call<SelfaCareResponse>
}
