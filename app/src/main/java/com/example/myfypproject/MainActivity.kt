package com.example.myfypproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.myfypproject.Base.BaseActivity
import com.example.myfypproject.FCM.FCMListener
import com.example.myfypproject.FCM.MyFirebaseService
import com.example.myfypproject.Fragment.*
import com.example.myfypproject.Fragment.Appointment.AppointmentFragment
import com.example.myfypproject.Fragment.Notification.NotificationFragment
import com.example.myfypproject.Fragment.Profile.ProfileFragment
import com.example.myfypproject.Model.UpdateFirebaseTokenRequest
import com.example.myfypproject.ViewModel.AppointmentViewModel
import com.example.myfypproject.ViewModel.ClickViewModel
import com.example.myfypproject.ViewModel.UserViewModel
import com.example.myfypproject.session.SessionManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    companion object {

        val TAG = MainActivity.javaClass.simpleName

        fun showMainActivity(activity: Activity) {
            val intent = Intent(activity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivity(intent)
        }
    }
    var pageTitle = ""

    override var titleOfView: String = ""
     val apptViewModel: AppointmentViewModel by viewModels()
    val userViewModel: UserViewModel by viewModels()
    val clickViewModel: ClickViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val homeFragment= HomeFragment()
        val appointmentFragment= AppointmentFragment()
        val notificationFragment = NotificationFragment()
        val profileFragment = ProfileFragment()
        setCurrentFragment("Home",homeFragment)
        attachObserver()
        attachFragmentObserver()
        UpdateFCMToken()
        btmNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> setCurrentFragment("Home",homeFragment)
                R.id.navigation_appt -> setCurrentFragment("Appointment",appointmentFragment)
                R.id.navigation_notification -> setCurrentFragment("Notification",notificationFragment)
                R.id.navigation_profile -> setCurrentFragment("Profile", profileFragment)
            }
            true
        }
    }
    private fun setCurrentFragment(pageTitle:String,fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            toolBarTitle(pageTitle)
            replace(R.id.fragment_container,fragment,pageTitle)
            commit()
        }

    private fun attachObserver(){
        apptViewModel.isLoadingVal.observe(this, {
           it?.let {showProgressBar(it) }
        })
        userViewModel.GeneralResponse.observe(this,{
            it?.let {
                //baseToastMessage(it?.message)
            }
        })

    }
    private fun attachFragmentObserver(){
        clickViewModel.fragmentNameVal.observe(this,{
             it?.let {
                 onFragmentChange(it)
             }
        })
    }
    private fun onFragmentChange(fragmentName : String){
        toolBarTitle(fragmentName)
    }
    override fun onBackPressed() {
        //val fraglist = supportFragmentManager.fragments
        val fragment = supportFragmentManager.findFragmentByTag("AD")
        if(fragment !=null && fragment?.isAdded!! && fragment.isVisible)
        {
            val notificationFragment= NotificationFragment()
            setCurrentFragment("Notification", notificationFragment)
        }
        else{
            shuntDownApp()
        }
    }
    private fun UpdateFCMToken(){
        val accId = SessionManager.GetaccId
        MyFirebaseService.retrieveToken(object :FCMListener{
            override fun onTokenRetrieveCallBack(token: String) {
                val FCMTokenRequest = UpdateFirebaseTokenRequest(accId,token)
                userViewModel.SendFCMToken(FCMTokenRequest)
            }
        })
    }
}