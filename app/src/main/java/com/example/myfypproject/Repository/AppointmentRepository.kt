package com.example.myfypproject.Repository

import android.util.Log
import com.example.myfypproject.Base.BaseUrl
import com.example.myfypproject.Model.Account
import com.example.myfypproject.Model.ApptListData
import com.example.myfypproject.Service.AppointmentService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

   suspend fun ApptListData(appt: ApptListData, successResponse:(ArrayList<ApptListData>?)->Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.GetAppointmentListData(appt)
       return withContext(Dispatchers.IO){
           api.enqueue(object : Callback<ArrayList<ApptListData>> {
            override fun onResponse(call: Call<ArrayList<ApptListData>>, response: Response<ArrayList<ApptListData>>) {
                response.body()?.let{
                    successResponse(it)
                }
            }
            override fun onFailure(call: Call<ArrayList<ApptListData>>, t: Throwable) {
                Log.d("failed called", t.message.toString())
            }
        })
       }
    }
   suspend fun ApptDetailData(appt: ApptListData, successResponse:(ArrayList<ApptListData>?)->Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.GetAppointmentDetailData(appt)
       return withContext(Dispatchers.IO) {
           api.enqueue(object : Callback<ArrayList<ApptListData>> {
               override fun onResponse(
                   call: Call<ArrayList<ApptListData>>,
                   response: Response<ArrayList<ApptListData>>
               ) {
                   response.body()?.let {
                       successResponse(it)
                   }
               }
               override fun onFailure(call: Call<ArrayList<ApptListData>>, t: Throwable) {
                   Log.d("failed called", t.message.toString())
               }
           })
       }
    }
}