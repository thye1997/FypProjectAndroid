package com.example.myfypproject.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfypproject.Model.Account
import com.example.myfypproject.Model.ApptListData
import com.example.myfypproject.Repository.AppointmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AppointmentViewModel: ViewModel(){
      var appointmentRepository = AppointmentRepository()
      var isLoading = MutableLiveData<Boolean>()
      var apiError = MutableLiveData<Throwable>()
      var apptListDataResponse = MutableLiveData<ArrayList<ApptListData>>()
      var apptDetailDataResponse = MutableLiveData<ArrayList<ApptListData>>()
      val isLoadingVal: LiveData<Boolean> get() = isLoading

         fun AppointmentListData(apptData: ApptListData){
             isLoading.value = true
           viewModelScope.launch(Dispatchers.IO){
               appointmentRepository.ApptListData(apptData,{
                   apptListDataResponse.value =it
                   isLoading.value = false
               },{
                   isLoading.value = false
                   apiError.value = it
               })
           }
         }
         fun AppointmentDetailData(apptData: ApptListData){
             isLoading.value = true
             viewModelScope.launch(Dispatchers.IO) {
                 appointmentRepository.ApptDetailData(apptData, {
                     apptDetailDataResponse.value = it
                     isLoading.value = false
                 }, {
                     isLoading.value = false
                     apiError.value = it
                 })
             }
         }
}