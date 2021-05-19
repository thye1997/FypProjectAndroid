package com.example.myfypproject.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfypproject.Model.*
import com.example.myfypproject.Repository.AppointmentRepository
import com.example.myfypproject.Repository.UserRepository


class UserViewModel : ViewModel() {

    var userRepository = UserRepository()
    var apiError = MutableLiveData<Throwable>()
    var isLoading = MutableLiveData<Boolean>()
    var LoginResponse = MutableLiveData<LoginResponse>()
    var DefaultProfileResponse = MutableLiveData<DefaultProfileResponse>()
    var SearchProfileResponse = MutableLiveData<ProfileSearchResponse>()
    var notificationPrefsResponse = MutableLiveData<NotificationPrefsResponse>()
    var GeneralResponse = MutableLiveData<GeneralResponse>()
    var NotificationPrefsUpdateResponse = MutableLiveData<GeneralResponse>()
    var DeleteProfileResponse = MutableLiveData<GeneralResponse>()
    var SwitchDefaultProfileResponse = MutableLiveData<GeneralResponse>()
    var ProfileDataResponse = MutableLiveData<DefaultProfileData>()
    var addNewProfileResponse = MutableLiveData<GeneralResponse>()
    var ProfileListResponse= MutableLiveData<ArrayList<ProfileListResponse>>()
    var relationshipVal = MutableLiveData<String>()
    var timeSlotSelect = MutableLiveData<Int>()
    var arrayValue = MutableLiveData<Array<String>>()

    fun LoginUser(loginRequest: LoginRequest) {
        isLoading.value = true
        userRepository.LoginAccount(loginRequest,
            {
                LoginResponse.value = it
                isLoading.value = false
            }, {
                isLoading.value = false
                apiError.value = it
            })
    }

    fun RegisterUser(loginRequest: LoginRequest) {
        isLoading.value = true
        userRepository.RegisterAccount(loginRequest,
            {
                LoginResponse.value = it
                isLoading.value = false
            }, {
                isLoading.value = false
                apiError.value = it
            })
        }

    fun CheckDefaultProfile(profileId: Int) {
        isLoading.value = true
        userRepository.DefaultProfile(profileId,
            {
                DefaultProfileResponse.value = it
                isLoading.value = false
            }, {
                isLoading.value = false
                apiError.value = it
            })
    }

    fun SearchExistProfile(NRIC: String) {
        isLoading.value = true
        userRepository.SearchExistProfile(NRIC,
            {
                SearchProfileResponse.value = it
                isLoading.value = false
            }, {
                isLoading.value = false
                apiError.value = it
            })
    }

    fun AddProfile(addProfile: AddProfileRequest){
        isLoading.value = true
        userRepository.AddProfile(addProfile,
            {
                GeneralResponse.value = it
                isLoading.value = false
            }, {
                isLoading.value = false
                apiError.value = it
            })
    }

    fun AddNewProfile(addNewProfile: AddNewProfileRequest){
        isLoading.value = true
        userRepository.AddNewProfile(addNewProfile,
            {
                addNewProfileResponse.value = it
                isLoading.value = false
            }, {
                isLoading.value = false
                apiError.value = it
            })
    }

    fun SendFCMToken(obj:UpdateFirebaseTokenRequest){
        isLoading.value = true
        userRepository.UpdateFCMToken(obj,{
            GeneralResponse.value =it
            isLoading.value = false
        },{
            isLoading.value = false
            apiError.value = it
        })
    }
    fun GetNotificationPrefs(accId:Int){
        isLoading.value = true
        userRepository.GetNotificationPrefs(accId,{
            notificationPrefsResponse.value =it
            isLoading.value = false
        },{
            isLoading.value = false
            apiError.value = it
        })
    }

    fun GetProfileList(accId:Int){
        isLoading.value = true
        userRepository.GetProfileList(accId,{
            ProfileListResponse.value =it
            isLoading.value = false
        },{
            isLoading.value = false
            apiError.value = it
        })
    }
    fun DeleteProfile(id:Int){
        isLoading.value = true
        userRepository.DeleteProfile(id,{
            DeleteProfileResponse.value =it
            isLoading.value = false
        },{
            isLoading.value = false
            apiError.value = it
        })
    }
    fun SwitchDefaultProfile(acc:SwitchProfileRequest){
        isLoading.value = true
        userRepository.SwitchDefaultProfile(acc,{
            SwitchDefaultProfileResponse.value =it
            isLoading.value = false
        },{
            isLoading.value = false
            apiError.value = it
        })
    }
    fun DefaultProfileData(defProfile:DefaultProfileData){
        isLoading.value = true
        userRepository.GetDefaultProfileData(defProfile,{
            ProfileDataResponse.value =it
            isLoading.value = false
        },{
            isLoading.value = false
            apiError.value = it
        })
    }

    fun UpdateNotificationPrefs(obj:UpdateNotificationPrefsRequest){
        isLoading.value = true
        userRepository.UpdateNotificationPrefs(obj,{
            NotificationPrefsUpdateResponse.value =it
            isLoading.value = false
        },{
            isLoading.value = false
            apiError.value = it
        })
    }

    fun SetClickedValue(value:String){
        relationshipVal.value = value
    }
    fun SetTimeSlotValue(value:Int){
        timeSlotSelect.value = value
    }

    fun SetArrayValue(array:Array<String>){
        arrayValue.value = array
    }
}