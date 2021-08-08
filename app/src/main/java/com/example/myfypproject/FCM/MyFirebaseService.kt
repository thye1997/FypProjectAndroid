package com.example.myfypproject.FCM

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myfypproject.MainActivity
import com.example.myfypproject.R
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseService: FirebaseMessagingService(){
   companion object{
       private val TAG = "MyFirebaseMsgService"
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
       Log.d("Token=>:", "token refreshed" + p0);
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.data["title"])

        val title: String
        val body: String
        Log.d(TAG, "Message data payload title: " + remoteMessage.data["title"].toString())
        Log.d(TAG, "Message data payload body: " + remoteMessage.data["body"].toString())

        title = remoteMessage.data["title"].toString()
        body = remoteMessage.data["body"].toString()
        sendNotification(title, body)
    }


    @SuppressLint("WrongConstant")
    private fun sendNotification(messageTitle: String, messageBody: String) {
        val stackBuilder = TaskStackBuilder.create(this)
        //add a parent activity once we tap on the notification that open the activity we defined in above
        //source of reference: https://stackoverflow.com/questions/45055606/going-back-to-main-activity-after-launching-activity-from-notification
        //stackBuilder.addParentStack(AddNewProfileActivity::class.java)
       // val resultPendingIntent: PendingIntent? = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            Intent.FLAG_ACTIVITY_CLEAR_TASK
        )

        val channelId = R.string.default_notification_channel_id.toString()
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.drawable.ic_baseline_person_24)
            setContentTitle(messageTitle)
            setStyle(NotificationCompat.BigTextStyle().bigText(messageBody))
            setContentText(messageBody)
            setAutoCancel(true)
            setSound(defaultSoundUri)
            priority = NotificationCompat.PRIORITY_HIGH
            setContentIntent(pendingIntent)
        }
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "My Fyp Project",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setShowBadge(true)
            }
            notificationManager.createNotificationChannel(channel)
        }
        with(NotificationManagerCompat.from(this)){
            notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
        }
    }


}