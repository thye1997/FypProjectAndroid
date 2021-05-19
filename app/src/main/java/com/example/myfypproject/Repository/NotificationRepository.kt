package com.example.myfypproject.Repository

import android.util.Log
import com.example.myfypproject.Base.BaseUrl
import com.example.myfypproject.Model.NotificationListResponse
import com.example.myfypproject.Model.ReminderListReponse
import com.example.myfypproject.Service.NotificationService
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NotificationRepository() {
    private val apiService: NotificationService

    init {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl.url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        apiService = retrofit.create(NotificationService::class.java)
    }

    fun NotificationList(successResponse:(ArrayList<NotificationListResponse>?)->Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.GeNotificationList()
        api.enqueue(object : Callback<ArrayList<NotificationListResponse>> {
            override fun onResponse(call: Call<ArrayList<NotificationListResponse>>, response: Response<ArrayList<NotificationListResponse>>) {
                response.body()?.let{
                    successResponse(it)
                }
            }
            override fun onFailure(call: Call<ArrayList<NotificationListResponse>>, t: Throwable) {
                Log.d("failed called", t.message.toString())
            }
        })
    }

    fun ReminderList(accId:Int, successResponse:(ArrayList<ReminderListReponse>?)->Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.GetReminderList(accId)
        api.enqueue(object : Callback<ArrayList<ReminderListReponse>> {
            override fun onResponse(call: Call<ArrayList<ReminderListReponse>>, response: Response<ArrayList<ReminderListReponse>>) {
                response.body()?.let{
                    successResponse(it)
                }
            }
            override fun onFailure(call: Call<ArrayList<ReminderListReponse>>, t: Throwable) {
                Log.d("failed called", t.message.toString())
            }
        })
    }
}