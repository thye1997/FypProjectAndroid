package com.example.myfypproject.Model

data class Account(
    val username: String,
    val password: String
)


data class ApptListData(
    val accId:Int,
    val apptId:Int=0,
    val fullName: String="",
    val phoneNumber: String="",
    val gender: String="",
    val dob: String="",
    val apptStatus:Array<Int>,
    val apptStatusString:String="",
    val service:String="",
    val date:String="",
    val startTime:String="",
    val endTime:String="",
    val isCheckIn:Boolean =false
)