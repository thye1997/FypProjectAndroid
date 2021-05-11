package com.example.myfypproject.Fragment.Profile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.myfypproject.Base.BaseFragment
import com.example.myfypproject.Base.FragmentName
import com.example.myfypproject.Base.FragmentType
import com.example.myfypproject.Fragment.Appointment.ApptTabAdapter
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.ClickViewModel
import com.example.myfypproject.ViewModel.NotificationViewModel
import com.example.myfypproject.session.SessionManager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_second.*

class ProfileFragment : BaseFragment() {
    override fun FragmentCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    override fun FragmentCreatedView(view: View, savedInstanceState: Bundle?) {
        initScreen()
    }
    override fun attachObserver() {
    }

    private fun initScreen(){
        logoutClickListener()
        notificationPrefClickListener()
        switchProfileClickListener()
        editProfileClickListener()
    }
    private fun logoutClickListener(){
        logout_layout.setOnClickListener {
            val builder = AlertDialog.Builder(context)

            builder.setMessage("Do you want to logout?")

            //performing positive action
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                SessionManager.logoutUser()
            }
            //performing cancel action
            builder.setNegativeButton("No") { dialogInterface, which ->
                dialogInterface.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }
    private fun notificationPrefClickListener(){
        notification_pref_layout.setOnClickListener {
            val notificationPrefFragment = NotificationPrefFragment()
            clickViewModel.SetCurrentFragment(FragmentName.NotificationPreferences)
            setFragmentWithBackStack(notificationPrefFragment,FragmentType.InnerFragment)
        }
    }
    private fun switchProfileClickListener(){
        switch_profile_layout.setOnClickListener {
            val switchProfileFragment = SwitchProfileFragment()
            clickViewModel.SetCurrentFragment(FragmentName.SwitchProfile)
            setFragmentWithBackStack(switchProfileFragment,FragmentType.InnerFragment)
        }
    }
    private fun editProfileClickListener(){
        edit_profile_layout.setOnClickListener {
            val editProfileFragment = EditProfileFragment()
            clickViewModel.SetCurrentFragment(FragmentName.EditProfile)
            setFragmentWithBackStack(editProfileFragment,FragmentType.InnerFragment)
        }
    }
}