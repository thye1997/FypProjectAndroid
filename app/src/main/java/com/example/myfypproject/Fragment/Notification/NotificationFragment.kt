package com.example.myfypproject.Fragment.Notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.myfypproject.Fragment.Appointment.ApptTabAdapter
import com.example.myfypproject.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_notification.*
import kotlinx.android.synthetic.main.fragment_second.*

class NotificationFragment : Fragment() {
    private lateinit var fragmentAdapter: NotificationTabAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initScreen()

    }

    private fun initScreen(){
        fragmentAdapter = NotificationTabAdapter(childFragmentManager, lifecycle)
        notification_viewPager.adapter = fragmentAdapter
        notification_tabLayout.addTab(notification_tabLayout.newTab().setText("Announcement"))
        notification_tabLayout.addTab(notification_tabLayout.newTab().setText("Reminder"))
        notification_viewPager.isSaveEnabled = false

        notification_tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    notification_viewPager.currentItem = tab.position
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        notification_viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                notification_tabLayout.selectTab(notification_tabLayout.getTabAt(position))
            }
        } )
    }
}