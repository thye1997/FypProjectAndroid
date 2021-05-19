package com.example.myfypproject.Service

import com.example.myfypproject.Model.ApptListData
import com.example.myfypproject.Model.DashboardDataCount
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface DashboardService {
    @POST("DashboardApi/RetrieveData")
    fun RetrievePatientData(@Body accId: Int): Call<DashboardDataCount>
}