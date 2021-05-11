package com.example.myfypproject.Service

import com.example.myfypproject.Model.Account
import com.example.myfypproject.Model.ApptListData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AppointmentService {
    @POST("AppointmentApi/AppointmentData")
    fun GetAppointmentListData(@Body appt: ApptListData): Call<ArrayList<ApptListData>>
    @POST("AppointmentApi/AppointmentData")
    fun GetAppointmentDetailData(@Body appt: ApptListData): Call<ArrayList<ApptListData>>
}