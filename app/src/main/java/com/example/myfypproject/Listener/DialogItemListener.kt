package com.example.myfypproject.Listener

interface DialogItemListener {
    fun onSelectedCallBack(position: Int)
    fun onGeneralSelectedCallBack(position: Int, type:String)
    fun onSelectedCallBack(value:String)
}