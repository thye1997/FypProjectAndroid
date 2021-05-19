package com.example.myfypproject.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfypproject.Model.*
import com.example.myfypproject.Repository.AppointmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AppointmentViewModel: ViewModel(){
      var appointmentRepository = AppointmentRepository()
      var isLoading = MutableLiveData<Boolean>()
      var apiError = MutableLiveData<Throwable>()
      var apptListDataResponse = MutableLiveData<ArrayList<ApptListData>>()
      var apptDetailDataResponse = MutableLiveData<ArrayList<ApptListData>>()
      var apptScheduleDataResponse = MutableLiveData<ApptSchedulData>()
      var specificSlotListResponse = MutableLiveData<ArrayList<SpecificSlotListResponse>>()
      var rescheduleAppointmentResponse = MutableLiveData<GeneralResponse>()
      var checkInAppointmentResponse = MutableLiveData<GeneralResponse>()
      var requestApptResponse = MutableLiveData<GeneralResponse>()
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

      fun AppointmentScheduleData(){
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            appointmentRepository.ApptScheduleData({
                apptScheduleDataResponse.value = it
                isLoading.value = false
            }, {
                isLoading.value = false
                apiError.value = it
            })
        }
    }
    fun GetSpecificSlotList(loadSlot:SpecificSlot){
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            appointmentRepository.LoadSpecificSlotList(loadSlot,{
                specificSlotListResponse.value = it
                isLoading.value = false
            }, {
                isLoading.value = false
                apiError.value = it
            })
        }
    }

    fun RescheduleAppointment(reschAppt:RescheduleAppointment){
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            appointmentRepository.RescheduleAppointment(reschAppt,{
                rescheduleAppointmentResponse.value = it
                isLoading.value = false
            }, {
                isLoading.value = false
                apiError.value = it
            })
        }
    }

    fun RequestAppointment(requestAppt:RequestAppointment){
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            appointmentRepository.RequestAppointment(requestAppt,{
                requestApptResponse.value = it
                isLoading.value = false
            }, {
                isLoading.value = false
                apiError.value = it
            })
        }
    }
    fun CheckInAppointmentQR(checkinAppt:CheckInAppointment){
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            appointmentRepository.CheckInAppointmentQR(checkinAppt,{
                checkInAppointmentResponse.value = it
                isLoading.value = false
            }, {
                isLoading.value = false
                apiError.value = it
            })
        }
    }
}