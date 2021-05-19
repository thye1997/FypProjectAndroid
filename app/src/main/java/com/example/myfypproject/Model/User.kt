package com.example.myfypproject.Model

data class LoginRequest(
    val EmailAddress: String,
    val password: String
)

data class LoginResponse(
    val accId:Int,
    val isSuccess: Boolean,
    val message: String
)

data class LoginErrorResponse(
    val isLogin: Boolean,
    val status: String,
    val message: String

)

data class DefaultProfileRequest(
    val ProfileId: Int
)

data class DefaultProfileResponse(
    val hasDefault: Boolean,
    val accProfileId: Int
)

data class ProfileSearchResponse(
    val profileId: Int,
    val fullName:String,
    val phoneNumber:String,
    val gender:String,
    val dob:String,
    val profileExist:Boolean
)

data class AddProfileRequest(
val profileId: Int,
val accId: Int,
val relationship: String,
val nric: String="",
val fullName:String="",
val phoneNumber:String="",
val gender:String="",
val dob:String="",
)
data class AddNewProfileRequest(
    val accId: Int,
    val relationship: String,
    val nric: String,
    var fullName:String,
    val phoneNumber:String,
    val gender:String,
    val dob:String,
)
data class DefaultProfileData(
    val accId:Int,
    val accountRegistered:String="",
    val profileId: Int=0,
    val nric: String="",
    val fullName:String="",
    val phoneNumber:String="",
    val gender:String="",
    val dob:String="",
)

data class SwitchProfileRequest(
    val accId:Int,
    val id:Int,
)
data class GeneralResponse(
    val isSuccess: Boolean,
    val message: String
)

data class UpdateFirebaseTokenRequest(
  val accId: Int,
  val firebaseToken: String
)

data class ProfileListResponse(
    val id:Int,
    val profileId: Int,
    val fullName: String,
    val relationship: String,
    var isDefault: Boolean
)

data class UpdateNotificationPrefsRequest(
    val accId: Int,
    val appPushNotification :Boolean,
    val appReminderPushNotification :Boolean,
    val smsReminderNotification: Boolean,
)

data class NotificationPrefsResponse(
    val appPushNotification :Boolean,
    val appReminderPushNotification :Boolean,
    val smsReminderNotification: Boolean,
    val hasAccount: Boolean
)