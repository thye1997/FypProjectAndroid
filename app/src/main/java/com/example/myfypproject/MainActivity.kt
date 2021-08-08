package com.example.myfypproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.myfypproject.Base.BaseActivity
import com.example.myfypproject.Base.BaseFragment
import com.example.myfypproject.Base.FragmentName
import com.example.myfypproject.Base.FragmentType
import com.example.myfypproject.FCM.FCMListener
import com.example.myfypproject.FCM.MyFirebaseService
import com.example.myfypproject.Fragment.*
import com.example.myfypproject.Fragment.Appointment.AddAppointmentFragment
import com.example.myfypproject.Fragment.Appointment.AppointmentDetailFragment
import com.example.myfypproject.Fragment.Appointment.AppointmentFragment
import com.example.myfypproject.Fragment.Appointment.RescheduleFragment
import com.example.myfypproject.Fragment.Notification.AnnouncementDetailFragment
import com.example.myfypproject.Fragment.Notification.AnnouncementFragment
import com.example.myfypproject.Fragment.Notification.NotificationFragment
import com.example.myfypproject.Fragment.Profile.EditProfileFragment
import com.example.myfypproject.Fragment.Profile.NotificationPrefFragment
import com.example.myfypproject.Fragment.Profile.ProfileFragment
import com.example.myfypproject.Fragment.Profile.SwitchProfileFragment
import com.example.myfypproject.Model.SwitchProfileRequest
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
    private lateinit var currentFrag: Fragment
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
                R.id.navigation_home -> setFragment<HomeFragment>(FragmentName.Home,homeFragment,null)
                R.id.navigation_appt -> setFragment<AppointmentFragment>(FragmentName.Appointment,appointmentFragment,null)
                R.id.navigation_notification -> setFragment<NotificationFragment>(FragmentName.Notification,notificationFragment,null)
                R.id.navigation_profile -> setFragment<ProfileFragment>(FragmentName.Profile, profileFragment, null)
            }
            true
        }
    }
    private fun setCurrentFragment(pageTitle:String,fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            currentFrag = fragment
            toolBarTitle(pageTitle)
            replace(R.id.fragment_container,fragment,pageTitle)
            setReorderingAllowed(true)
            commit()
        }
    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
     internal inline fun <reified T> setFragment(title:String,fragment:Fragment, bundle: Bundle?) where T: BaseFragment =
        run {
            currentFrag = fragment
            toolBarTitle(title)
            this?.supportFragmentManager?.commit {
                setReorderingAllowed(true)
                replace<T>(R.id.fragment_container,null,bundle)
            }
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
        clickViewModel.CurrentFrag.observe(this,{
             it?.let {
                 //baseToastMessage(it.javaClass.name)
                 currentFrag = it
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
                backToPreviousFrag()
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
    }
    override fun onBackPressed() {
        backToPreviousFrag()
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
                val bundle = bundleOf("apptId" to apptId)
                setFragment<QRCodeFragment>(FragmentName.CheckIn, QRCodeFragment(),bundle)
            }
            true
        }
    }
    private fun backToPreviousFrag(){
        if(currentFrag is HomeFragment || currentFrag is AppointmentFragment || currentFrag is NotificationFragment || currentFrag is ProfileFragment ){
            shuntDownApp()
        }
        if(currentFrag is AppointmentDetailFragment){
            val appointmentFragment = AppointmentFragment()
            setFragment<AppointmentFragment>(FragmentName.Appointment,appointmentFragment,null)
        }
        else if (currentFrag is AddAppointmentFragment){
            setFragment<HomeFragment>(FragmentName.Home,HomeFragment(),null)
        }
        else if(currentFrag is EditProfileFragment || currentFrag is SwitchProfileFragment || currentFrag is NotificationPrefFragment){
            setFragment<ProfileFragment>(FragmentName.Profile,ProfileFragment(),null)
        }
        else if (currentFrag is AnnouncementDetailFragment){
            setFragment<NotificationFragment>(FragmentName.Notification,NotificationFragment(),null)
        }
        else if (currentFrag is QRCodeFragment || currentFrag is RescheduleFragment){
            val bundle = bundleOf("apptId" to apptId)
            setFragment<AppointmentDetailFragment>(FragmentName.AppointmentDetail,AppointmentDetailFragment(),bundle)

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