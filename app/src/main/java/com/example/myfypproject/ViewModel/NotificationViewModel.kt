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
    var reminderListResponse = MutableLiveData<ArrayList<ReminderListReponse>>()

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

    fun ReminderList(accId:Int) {
        isLoading.value = true
        notificationRepository.ReminderList(accId,
            {
                reminderListResponse.value = it
                isLoading.value = false
            }, {
                isLoading.value = false
                apiError.value = it
            })
    }
}