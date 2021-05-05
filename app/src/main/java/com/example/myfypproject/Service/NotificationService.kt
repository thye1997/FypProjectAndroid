package com.example.myfypproject.Service

import com.example.myfypproject.Model.Account
import com.example.myfypproject.Model.NotificationListResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationService {
    @POST("NotificationApi/GetNotificationList")
    fun GeNotificationList(): Call<ArrayList<NotificationListResponse>>
}