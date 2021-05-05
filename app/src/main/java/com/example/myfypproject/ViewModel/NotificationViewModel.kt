package com.example.myfypproject.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfypproject.Model.*
import com.example.myfypproject.Repository.NotificationRepository
import com.example.myfypproject.Repository.UserRepository

class NotificationViewModel : ViewModel() {

    var notificationRepository = NotificationRepository()
    var apiError = MutableLiveData<Throwable>()
    var isLoading = MutableLiveData<Boolean>()
    var notificationListResponse= MutableLiveData<ArrayList<NotificationListResponse>>()

    fun NotificationList() {
        isLoading.value = true
        notificationRepository.NotificationList(
            {
                notificationListResponse.value = it
                isLoading.value = false
            }, {
                isLoading.value = false
                apiError.value = it
            })
    }
}