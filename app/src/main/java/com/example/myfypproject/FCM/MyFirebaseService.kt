package com.example.myfypproject.FCM

import android.util.Log
import androidx.activity.viewModels
import com.example.myfypproject.MainActivity
import com.example.myfypproject.ViewModel.UserViewModel
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService


class MyFirebaseService: FirebaseMessagingService(){
   companion object{
       fun retrieveToken(fcmListener: FCMListener) {
           FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
               if (!task.isSuccessful) {
               }
               fcmListener.onTokenRetrieveCallBack(task.result.toString())
           }
           //Log.d(MainActivity.TAG, token)
       }
   }
    override fun onNewToken(p0: String) {
       Log.d("Token=>:", "token refreshed"+ p0);
    }


}