package com.example.myfypproject.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfypproject.Model.ApptListData
import com.example.myfypproject.Model.DashboardDataCount
import com.example.myfypproject.Repository.AppointmentRepository
import com.example.myfypproject.Repository.DashboardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel: ViewModel(){
    var dashboardRepository = DashboardRepository()
    var isLoading = MutableLiveData<Boolean>()
    var apiError = MutableLiveData<Throwable>()
    var dashboardDataResponse = MutableLiveData<DashboardDataCount>()


    fun DashboardData(accId: Int){
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO){
            dashboardRepository.RetrieveDashboardData(accId,{
                dashboardDataResponse.value =it
                isLoading.value = false
            },{
                isLoading.value = false
                apiError.value = it
            })
        }
    }





}