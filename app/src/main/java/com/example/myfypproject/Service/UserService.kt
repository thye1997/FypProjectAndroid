package com.example.myfypproject.Service

import com.example.myfypproject.Model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
        @POST("AccountApi/Login")
        fun AccountLogin(@Body login: LoginRequest): Call<LoginResponse>

        @POST("AccountApi/Register")
        fun AccountRegister(@Body register: LoginRequest): Call<LoginResponse>

        @POST("AccountApi/DefaultProfile")
        fun DefaultProfile(@Body accId: Int): Call<DefaultProfileResponse>

        @POST("AccountApi/SearchProfile")
        fun SearchProfile(@Body NRIC: String): Call<ProfileSearchResponse>

        @POST("AccountApi/AddProfile")
        fun AddProfile(@Body addProfile: AddProfileRequest): Call<GeneralResponse>

        @POST("AccountApi/AddNewProfile")
        fun AddNewProfile(@Body addNewProfile: AddNewProfileRequest): Call<GeneralResponse>

        @POST("AccountApi/ProfileList")
        fun GetProfileList(@Body accId: Int): Call<ArrayList<ProfileListResponse>>

        @POST("AccountApi/DeleteProfile")
        fun DeleteProfile(@Body Id: Int): Call<GeneralResponse>

        @POST("AccountApi/SwitchDefaultProfile")
        fun SwitchDefaultProfile(@Body acc:SwitchProfileRequest): Call<GeneralResponse>

        @POST("AccountApi/UpdateFirebaseToken")
        fun UpdateFirebaseToken(@Body addProfile: UpdateFirebaseTokenRequest): Call<GeneralResponse>

        @POST("AccountApi/GetNotificationPrefs")
        fun GetNotificationSettings(@Body accId: Int): Call<NotificationPrefsResponse>

        @POST("AccountApi/NotificationPrefsUpdate")
        fun UpdateNotificationPrefs(@Body updateNotificationPrefsRequest: UpdateNotificationPrefsRequest): Call<GeneralResponse>

        @POST("AccountApi/GetDefaultProfileData")
        fun DefaultProfileData(@Body defaultProfileData: DefaultProfileData): Call<DefaultProfileData>

}