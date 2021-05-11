package com.example.myfypproject.ViewModel

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ClickViewModel :ViewModel(){

    var fragmentNameVal = MutableLiveData<String>()
    var isLoading = MutableLiveData<Boolean>()
    var isCheckIn = MutableLiveData<Boolean>()
    fun SetCurrentFragment(fragmentName:String){
        fragmentNameVal.value = fragmentName
    }

    fun SetProgressBar(showProgress:Boolean){
        isLoading.value = showProgress
    }

    fun SetIsCheckIn(checkIn:Boolean){
        isCheckIn.value = checkIn
    }
}