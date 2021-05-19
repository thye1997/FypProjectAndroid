package com.example.myfypproject.Fragment.Appointment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myfypproject.Base.ApptType


class ApptTabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
         when (position) {
            1 -> return UpcomingFragment(ApptType.Past)
            2 -> return UpcomingFragment(ApptType.NoShow)
        }
         return UpcomingFragment(ApptType.Upcoming)
    }
}