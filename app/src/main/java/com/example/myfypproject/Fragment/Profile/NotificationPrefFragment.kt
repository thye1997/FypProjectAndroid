package com.example.myfypproject.Fragment.Profile

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.myfypproject.Model.GeneralResponse
import com.example.myfypproject.Model.NotificationPrefsResponse
import com.example.myfypproject.Model.UpdateNotificationPrefsRequest
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.NotificationViewModel
import com.example.myfypproject.ViewModel.UserViewModel
import com.example.myfypproject.session.SessionManager
import kotlinx.android.synthetic.main.fragment_notification_pref.*
import kotlinx.android.synthetic.main.fragment_profile.*

class NotificationPrefFragment : Fragment(),CompoundButton.OnCheckedChangeListener {
    var app_push_val:Boolean= false
    var appt_push_val:Boolean= false
    var app_sms_push_val:Boolean= false
    var accId = SessionManager.GetaccId
    private val userViewModel: UserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification_pref, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        app_push_toggle.setOnCheckedChangeListener(null)
        appt_app_push_toggle.setOnCheckedChangeListener(null)
        appt_sms_push_toggle.setOnCheckedChangeListener(null)
        attachObserver()
        userViewModel.GetNotificationPrefs(SessionManager.GetaccId)
    }
    private fun initScreen(){
        app_push_toggle.setOnCheckedChangeListener(this)
        appt_app_push_toggle.setOnCheckedChangeListener(this)
        appt_sms_push_toggle.setOnCheckedChangeListener(this)
    }
    private fun attachObserver(){
        userViewModel.notificationPrefsResponse.observe(viewLifecycleOwner,{
            it?.let {
                switchButtonState(it)
                initScreen()
            }
        })
        userViewModel.NotificationPrefsUpdateResponse.observe(viewLifecycleOwner,{
            it?.let {
                Toast.makeText(activity,it?.message,Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun switchButtonState(it: NotificationPrefsResponse){
        if(it?.hasAccount){
            app_push_val = it?.appPushNotification
            app_push_toggle.isChecked = it?.appPushNotification

            appt_push_val = it?.appReminderPushNotification
            appt_app_push_toggle.isChecked = it?.appReminderPushNotification

            app_sms_push_val = it?.smsReminderNotification
            appt_sms_push_toggle.isChecked = it?.smsReminderNotification
        }
    }
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            when(buttonView?.id){
                  R.id.app_push_toggle-> getState(buttonView.id, isChecked)
                  R.id.appt_app_push_toggle-> getState(buttonView.id,isChecked)
                  R.id.appt_sms_push_toggle-> getState(buttonView.id,isChecked)
            }
    }
    private fun getState(id: Int,isChecked: Boolean){
        if(id == R.id.app_push_toggle){
            app_push_val = isChecked
        }
        if(id == R.id.appt_app_push_toggle){
            appt_push_val = isChecked
        }
        if(id == R.id.appt_sms_push_toggle){
            app_sms_push_val = isChecked
        }
        val obj = UpdateNotificationPrefsRequest(accId,app_push_val,appt_push_val,app_sms_push_val)
        userViewModel.UpdateNotificationPrefs(obj)
    }
}