package com.example.myfypproject.Service

import com.example.myfypproject.Model.Account
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AppointmentService {
    @POST("DashboardApi/testApi")
    fun FirstTry(@Body user: Account): Call<Account>

}