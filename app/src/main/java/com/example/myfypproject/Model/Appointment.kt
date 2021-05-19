package com.example.myfypproject.Model

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
    val slot:String="",
    val startTime:String="",
    val endTime:String="",
    val note:String="",
    val isCheckIn:Boolean =false,
    val isAction:Boolean = false
)

data class SpecificSlot(
    val date: String,
    val slot: Int
)

data class RescheduleAppointment(
    val actionType:Int,
    val apptId:Int,
    val date:String,
    val startTime:String
)

data class RequestAppointment(
    val accId: Int,
    val date: String,
    val startTime: String,
    val serviceId:Int,
    val note: String
)

data class CheckInAppointment(
    val apptId:Int,
    val uniqueString:String
)


data class SpecificSlotListResponse(
    val slot:String,
    val startTime:String,
    val slotKey:String
)

data class ServiceListResponse(
    val id:Int,
    val serviceName:String,
    val createdOn:String,
    val createdBy:String,
    val isActive:Boolean
)

data class ApptSchedulData(
    val spHolidayList:Array<SpecialHoliday>,
    val offDay:Array<Int>,
    val serviceList:Array<ServiceListResponse>
)

data class SpecialHoliday(
    val date:String
)