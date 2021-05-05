package com.example.myfypproject.Fragment.Notification

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myfypproject.Fragment.Appointment.NoShowFragment
import com.example.myfypproject.Fragment.Appointment.PastFragment
import com.example.myfypproject.Fragment.Appointment.UpcomingFragment

class NotificationTabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            1 -> return AnnouncementFragment()
        }
        return AnnouncementFragment()
    }
}