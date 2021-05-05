package com.example.myfypproject.Repository

import android.util.Log
import com.example.myfypproject.Base.BaseUrl
import com.example.myfypproject.Model.*
import com.example.myfypproject.Service.AppointmentService
import com.example.myfypproject.Service.UserService
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepository() {
    private val apiService: UserService

    init {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl.url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        apiService = retrofit.create(UserService::class.java)
    }

    fun RegisterAccount (loginRequest: LoginRequest, successResponse: (LoginResponse?) -> Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.AccountRegister(loginRequest)
        api.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                when{
                    response.isSuccessful ->{
                        response.body()?.let {
                            successResponse(it)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                errorHandler(t)
            }

        })
    }
    fun LoginAccount (loginRequest: LoginRequest, successResponse: (LoginResponse?) -> Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.AccountLogin(loginRequest)
        api.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                when{
                    response.isSuccessful ->{
                        response.body()?.let {
                            successResponse(it)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                errorHandler(t)
            }

        })
    }
    fun DefaultProfile (accId: Int, successResponse: (DefaultProfileResponse?) -> Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.DefaultProfile(accId)
        api.enqueue(object : Callback<DefaultProfileResponse>{
            override fun onResponse(call: Call<DefaultProfileResponse>, response: Response<DefaultProfileResponse>) {
                when{
                    response.isSuccessful ->{
                        response.body()?.let {
                            successResponse(it)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<DefaultProfileResponse>, t: Throwable) {
                errorHandler(t)
            }
        })
    }
    fun SearchExistProfile (NRIC: String, successResponse: (ProfileSearchResponse?) -> Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.SearchProfile(NRIC)
        api.enqueue(object : Callback<ProfileSearchResponse>{
            override fun onResponse(call: Call<ProfileSearchResponse>, response: Response<ProfileSearchResponse>) {
                when{
                    response.isSuccessful ->{
                        response.body()?.let {
                            successResponse(it)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<ProfileSearchResponse>, t: Throwable) {
                errorHandler(t)
            }
        })
    }
    fun AddProfile (addProfile: AddProfileRequest, successResponse: (GeneralResponse?) -> Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.AddProfile(addProfile)
        api.enqueue(object : Callback<GeneralResponse>{
            override fun onResponse(call: Call<GeneralResponse>, response: Response<GeneralResponse>) {
                when{
                    response.isSuccessful ->{
                        response.body()?.let {
                            successResponse(it)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                errorHandler(t)
            }
        })
    }
    fun GetProfileList (accId: Int, successResponse: (ArrayList<ProfileListResponse>?) -> Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.GetProfileList(accId)
        api.enqueue(object : Callback<ArrayList<ProfileListResponse>>{
            override fun onResponse(call: Call<ArrayList<ProfileListResponse>>, response: Response<ArrayList<ProfileListResponse>>) {
                when{
                    response.isSuccessful ->{
                        response.body()?.let {
                            successResponse(it)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<ArrayList<ProfileListResponse>>, t: Throwable) {
                errorHandler(t)
            }
        })
    }
    fun DeleteProfile (id: Int, successResponse: (GeneralResponse?) -> Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.DeleteProfile(id)
        api.enqueue(object : Callback<GeneralResponse>{
            override fun onResponse(call: Call<GeneralResponse>, response: Response<GeneralResponse>) {
                when{
                    response.isSuccessful ->{
                        response.body()?.let {
                            successResponse(it)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                errorHandler(t)
            }
        })
    }
    fun SwitchDefaultProfile (acc:SwitchProfileRequest, successResponse: (GeneralResponse?) -> Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.SwitchDefaultProfile(acc)
        api.enqueue(object : Callback<GeneralResponse>{
            override fun onResponse(call: Call<GeneralResponse>, response: Response<GeneralResponse>) {
                when{
                    response.isSuccessful ->{
                        response.body()?.let {
                            successResponse(it)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                errorHandler(t)
            }
        })
    }

    fun UpdateFCMToken (obj: UpdateFirebaseTokenRequest, successResponse: (GeneralResponse?) -> Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.UpdateFirebaseToken(obj)
        api.enqueue(object : Callback<GeneralResponse>{
            override fun onResponse(call: Call<GeneralResponse>, response: Response<GeneralResponse>) {
                when{
                    response.isSuccessful ->{
                        response.body()?.let {
                            successResponse(it)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                errorHandler(t)
            }
        })
    }
    fun GetNotificationPrefs (accId: Int, successResponse: (NotificationPrefsResponse?) -> Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.GetNotificationSettings(accId)
        api.enqueue(object : Callback<NotificationPrefsResponse>{
            override fun onResponse(call: Call<NotificationPrefsResponse>, response: Response<NotificationPrefsResponse>) {
                when{
                    response.isSuccessful ->{
                        response.body()?.let {
                            successResponse(it)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<NotificationPrefsResponse>, t: Throwable) {
                errorHandler(t)
            }
        })
    }
    fun UpdateNotificationPrefs (obj: UpdateNotificationPrefsRequest, successResponse: (GeneralResponse?) -> Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.UpdateNotificationPrefs(obj)
        api.enqueue(object : Callback<GeneralResponse>{
            override fun onResponse(call: Call<GeneralResponse>, response: Response<GeneralResponse>) {
                when{
                    response.isSuccessful ->{
                        response.body()?.let {
                            successResponse(it)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                errorHandler(t)
            }
        })
    }



}