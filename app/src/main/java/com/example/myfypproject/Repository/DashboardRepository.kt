package com.example.myfypproject.Repository

import android.util.Log
import com.example.myfypproject.Base.BaseUrl
import com.example.myfypproject.Model.ApptListData
import com.example.myfypproject.Model.DashboardDataCount
import com.example.myfypproject.Service.AppointmentService
import com.example.myfypproject.Service.DashboardService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DashboardRepository() {
    private val apiService: DashboardService

    init {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl.url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        apiService = retrofit.create(DashboardService::class.java)
    }

    suspend fun RetrieveDashboardData(accId: Int, successResponse:(DashboardDataCount?)->Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.RetrievePatientData(accId)
        return withContext(Dispatchers.IO){
            api.enqueue(object : Callback<DashboardDataCount> {
                override fun onResponse(call: Call<DashboardDataCount>, response: Response<DashboardDataCount>) {
                    response.body()?.let{
                        successResponse(it)
                    }
                }
                override fun onFailure(call: Call<DashboardDataCount>, t: Throwable) {
                    Log.d("failed called", t.message.toString())
                }
            })
        }
    }
    }