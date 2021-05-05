package com.example.myfypproject.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfypproject.Model.Account
import com.example.myfypproject.Repository.AppointmentRepository


     class AppointmentViewModel: ViewModel(){
      var appointmentRepository = AppointmentRepository()
      var isLoading = MutableLiveData<Boolean>()
      var userDataResponse = MutableLiveData<Account>()
      val isLoadingVal: LiveData<Boolean> get() = isLoading
      fun GetList(userData: Account){
          isLoading.value = true
          appointmentRepository.firstApiCall(
              userData,
          ){
              userDataResponse.value = it
              isLoading.value = false
          }
      }
}