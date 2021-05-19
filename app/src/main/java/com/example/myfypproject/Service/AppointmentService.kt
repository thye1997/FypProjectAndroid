package com.example.myfypproject.Service

import com.example.myfypproject.Model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AppointmentService {
    @POST("AppointmentApi/AppointmentData")
    fun GetAppointmentListData(@Body appt: ApptListData): Call<ArrayList<ApptListData>>
    @POST("AppointmentApi/AppointmentData")
    fun GetAppointmentDetailData(@Body appt: ApptListData): Call<ArrayList<ApptListData>>
    @POST("AppointmentApi/GetAppointmentConfigData")
    fun GetApptScheduleData(): Call<ApptSchedulData>
    @POST("AppointmentApi/LoadSpecificSlot")
    fun LoadSpecificSlot(@Body loadSlot:SpecificSlot): Call<ArrayList<SpecificSlotListResponse>>
    @POST("AppointmentApi/ChangeAppointmentStatus")
    fun ChangeAppointmentStatus(@Body rescheduleAppt:RescheduleAppointment): Call<GeneralResponse>
    @POST("AppointmentApi/AddAppointment")
    fun RequestAppointment(@Body requestAppt:RequestAppointment): Call<GeneralResponse>
    @POST("AppointmentApi/CheckInAppointment")
    fun CheckInAppointmentQR(@Body checkInAppt:CheckInAppointment): Call<GeneralResponse>
}