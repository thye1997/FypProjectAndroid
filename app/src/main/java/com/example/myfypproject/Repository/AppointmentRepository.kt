package com.example.myfypproject.Repository

import android.util.Log
import com.example.myfypproject.Base.BaseUrl
import com.example.myfypproject.Model.*
import com.example.myfypproject.Service.AppointmentService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppointmentRepository() {
    private val apiService: AppointmentService

    init {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl.url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        apiService = retrofit.create(AppointmentService::class.java)
    }

   suspend fun ApptListData(appt: ApptListData, successResponse:(ArrayList<ApptListData>?)->Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.GetAppointmentListData(appt)
       return withContext(Dispatchers.IO){
           api.enqueue(object : Callback<ArrayList<ApptListData>> {
            override fun onResponse(call: Call<ArrayList<ApptListData>>, response: Response<ArrayList<ApptListData>>) {
                response.body()?.let{
                    successResponse(it)
                }
            }
            override fun onFailure(call: Call<ArrayList<ApptListData>>, t: Throwable) {
                Log.d("failed called", t.message.toString())
            }
        })
       }
    }
   suspend fun ApptDetailData(appt: ApptListData, successResponse:(ArrayList<ApptListData>?)->Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.GetAppointmentDetailData(appt)
       return withContext(Dispatchers.IO) {
           api.enqueue(object : Callback<ArrayList<ApptListData>> {
               override fun onResponse(
                   call: Call<ArrayList<ApptListData>>,
                   response: Response<ArrayList<ApptListData>>
               ) {
                   response.body()?.let {
                       successResponse(it)
                   }
               }
               override fun onFailure(call: Call<ArrayList<ApptListData>>, t: Throwable) {
                   Log.d("failed called", t.message.toString())
               }
           })
       }
    }

    suspend fun ApptScheduleData(successResponse:(ApptSchedulData?)->Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.GetApptScheduleData()
        return withContext(Dispatchers.IO) {
            api.enqueue(object : Callback<ApptSchedulData> {
                override fun onResponse(
                    call: Call<ApptSchedulData>,
                    response: Response<ApptSchedulData>
                ) {
                    response.body()?.let {
                        successResponse(it)
                    }
                }
                override fun onFailure(call: Call<ApptSchedulData>, t: Throwable) {
                    Log.d("failed called", t.message.toString())
                }
            })
        }
    }
    suspend fun LoadSpecificSlotList( loadSlot:SpecificSlot,successResponse:(ArrayList<SpecificSlotListResponse>?)->Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.LoadSpecificSlot(loadSlot)
        return withContext(Dispatchers.IO) {
            api.enqueue(object : Callback<ArrayList<SpecificSlotListResponse>> {
                override fun onResponse(
                    call: Call<ArrayList<SpecificSlotListResponse>>,
                    response: Response<ArrayList<SpecificSlotListResponse>>
                ) {
                    response.body()?.let {
                        successResponse(it)
                    }
                }
                override fun onFailure(call: Call<ArrayList<SpecificSlotListResponse>>, t: Throwable) {
                    Log.d("failed called", t.message.toString())
                }
            })
        }
    }
    suspend fun RescheduleAppointment( rescheduleAppt:RescheduleAppointment,successResponse:(GeneralResponse?)->Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.ChangeAppointmentStatus(rescheduleAppt)
        return withContext(Dispatchers.IO) {
            api.enqueue(object : Callback<GeneralResponse> {
                override fun onResponse(
                    call: Call<GeneralResponse>,
                    response: Response<GeneralResponse>
                ) {
                    response.body()?.let {
                        successResponse(it)
                    }
                }
                override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                    Log.d("failed called", t.message.toString())
                }
            })
        }
    }
    suspend fun RequestAppointment( requestAppt:RequestAppointment,successResponse:(GeneralResponse?)->Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.RequestAppointment(requestAppt)
        return withContext(Dispatchers.IO) {
            api.enqueue(object : Callback<GeneralResponse> {
                override fun onResponse(
                    call: Call<GeneralResponse>,
                    response: Response<GeneralResponse>
                ) {
                    response.body()?.let {
                        successResponse(it)
                    }
                }
                override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                    Log.d("failed called", t.message.toString())
                }
            })
        }
    }
    suspend fun CheckInAppointmentQR( checkInAppt:CheckInAppointment,successResponse:(GeneralResponse?)->Unit, errorHandler:(Throwable?)->Unit){
        val api = apiService.CheckInAppointmentQR(checkInAppt)
        return withContext(Dispatchers.IO) {
            api.enqueue(object : Callback<GeneralResponse> {
                override fun onResponse(
                    call: Call<GeneralResponse>,
                    response: Response<GeneralResponse>
                ) {
                    response.body()?.let {
                        successResponse(it)
                    }
                }
                override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                    Log.d("failed called", t.message.toString())
                }
            })
        }
    }
}