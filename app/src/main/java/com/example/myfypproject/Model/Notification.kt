package com.example.myfypproject.Model

data class NotificationListResponse(
    val id: Int,
    val title:String,
    val body: String,
    val date: String
)

data class ReminderListReponse(
    val content:String
)
