package com.example.myfypproject.Repository

import android.util.Log
import com.example.myfypproject.Base.BaseUrl
import com.example.myfypproject.Model.Test
import com.example.myfypproject.Service.AppointmentService
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppointmentRepository() {
    private val apiService: AppointmentService

    init {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl.url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        apiService = retrofit.create(AppointmentService::class.java)
    }

    fun firstApiCall(user: Test){
        val api = apiService.FirstTry(user)

        api.enqueue(object : Callback<Test> {
            override fun onResponse(call: Call<Test>, response: Response<Test>) {
                Log.d("firstApiCall", response.body()?.age)
            }

            override fun onFailure(call: Call<Test>, t: Throwable) {
                Log.d("failed called", t.message.toString())

            }

        })
    }


}