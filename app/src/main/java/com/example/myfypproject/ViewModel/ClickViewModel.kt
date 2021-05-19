package com.example.myfypproject.ViewModel

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ClickViewModel :ViewModel(){

    var fragmentNameVal = MutableLiveData<String>()
    var isLoading = MutableLiveData<Boolean>()
    var isCheckIn = MutableLiveData<Boolean>()
    var apptIdVal = MutableLiveData<Int>()
    var fragmentTitle = MutableLiveData<String>()
    var backPress = MutableLiveData<Boolean>()
    fun SetCurrentFragment(fragmentName:String){
        fragmentNameVal.value = fragmentName
    }

    fun SetFragmentTitle(title:String){
        fragmentTitle.value = title
    }

    fun SetProgressBar(showProgress:Boolean){
        isLoading.value = showProgress
    }

    fun SetIsCheckIn(checkIn:Boolean, apptId:Int){
        isCheckIn.value = checkIn
        apptIdVal.value = apptId
    }

    fun triggerBackPress(){
        backPress.value = true
    }
}