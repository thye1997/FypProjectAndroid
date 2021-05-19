package com.example.myfypproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.myfypproject.Base.BaseActivity
import com.example.myfypproject.Base.FragmentName
import com.example.myfypproject.Base.FragmentType
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
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

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
    override var titleOfView: String = ""
     val apptViewModel: AppointmentViewModel by viewModels()
    val userViewModel: UserViewModel by viewModels()
    val clickViewModel: ClickViewModel by viewModels()
    private lateinit var currentFragment:String
    private var apptId by Delegates.notNull<Int>()
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
            setReorderingAllowed(true)
            commit()
        }
    private fun setCurrentFragmentToStack(fragment: Fragment, type:String, bundle: Bundle?=null)=
        supportFragmentManager.beginTransaction().apply {
            replace(
                R.id.fragment_container,
                fragment, FragmentType.InnerFragment
            ).addToBackStack(null)
            setReorderingAllowed(true)
            commit()
        }
    private fun attachObserver(){
        apptViewModel.isLoadingVal.observe(this, {
           it?.let {showProgressBar(it) }
        })
        userViewModel.GeneralResponse.observe(this,{
            it?.let {
            }
        })
    }
    private fun attachFragmentObserver(){
        clickViewModel.fragmentNameVal.observe(this,{
             it?.let {
                 onFragmentChange(it)
             }
        })
        clickViewModel.isLoading.observe(this,{
            showProgressBar(it)
        })
        clickViewModel.isCheckIn.observe(this,{
                inflateQRIcon(it)
        })
        clickViewModel.fragmentTitle.observe(this,{
            toolBarTitle(it)
        })
        clickViewModel.backPress.observe(this,{
            if(it){
                checkFragmentState()
            }
        })
        clickViewModel.apptIdVal.observe(this,{
            apptId = it
        })
    }
    private fun onFragmentChange(fragmentName : String){
        toolBarTitle(fragmentName)
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        currentFragment = fragment.tag.toString()
        //baseToastMessage(currentFragment)
    }
    override fun onBackPressed() {
        checkFragmentState()
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
    private fun inflateQRIcon(it: Boolean){
        if(it){
            if(baseToolbar.findViewById<View>(R.id.qr_code_btn)==null){
                baseToolbar.inflateMenu(R.menu.qr_menu)
            }
        }
        else{
            baseToolbar.menu.clear()
        }
        base_toolbar.setOnMenuItemClickListener {
            if(it.itemId ==R.id.qr_code_btn){
                val qrCodeFragment = QRCodeFragment.newInstance(apptId)
                toolBarTitle(FragmentName.CheckIn)
                setCurrentFragmentToStack(qrCodeFragment,FragmentType.InnerFragment)
            }
            true
        }
    }
    private fun checkFragmentState(){

        val fragment = supportFragmentManager
        val backStackFragment = fragment.findFragmentByTag(FragmentType.InnerFragment)
        val totalFrag = fragment.backStackEntryCount
        for(i in 0 until totalFrag){
            //fragment?.getBackStackEntryAt(i)?.name
            fragment.getBackStackEntryAt(i).name.let { it->
                if (it != null) {
                    Log.d("Fragment list", it)
                }
            }
        }
        if(fragment !=null){
            if(backStackFragment !=null && backStackFragment?.isAdded!! && backStackFragment.isVisible){

                fragment.popBackStack()
            }
            else{
                shuntDownApp()
            }
        }
        else{
            shuntDownApp()
        }
    }
}