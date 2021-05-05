package com.example.myfypproject.ViewModel

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ClickViewModel :ViewModel(){

    var fragmentNameVal = MutableLiveData<String>()

    fun SetCurrentFragment(fragmentName:String){
        fragmentNameVal.value = fragmentName
    }
}