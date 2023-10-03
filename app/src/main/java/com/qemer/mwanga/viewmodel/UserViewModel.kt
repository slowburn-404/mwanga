package com.qemer.mwanga.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qemer.mwanga.models.LoginRequest
import com.qemer.mwanga.models.LoginResponse
//import com.qemer.mwanga.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel:ViewModel() {
    //populate with live data otherwise throw an error
    //instantiate
//    val userRepo = UserRepository()
    val regLiveDataLogin = MutableLiveData<LoginResponse>()
    val errLiveData = MutableLiveData<String>()



    // You cant invoke suspend fun outside coroutine
//    fun loginUser(loginRequest: LoginRequest){
//        viewModelScope.launch{
////            val responselogin = userRepo.login(loginRequest)
//            if (responselogin.isSuccessful){
//                regLiveDataLogin.postValue(responselogin.body())
//            }
//            else{
//                errLiveData.postValue(responselogin.errorBody()?.string())
//            }
//
//        }
//    }

}