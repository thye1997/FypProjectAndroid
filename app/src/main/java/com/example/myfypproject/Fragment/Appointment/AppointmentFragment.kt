package com.example.myfypproject.Fragment.Appointment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.myfypproject.Base.BaseFragment
import com.example.myfypproject.Base.FragmentName
import com.example.myfypproject.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_second.*

class AppointmentFragment : BaseFragment() {
    private lateinit var fragmentAdapter: ApptTabAdapter
    override fun FragmentCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun FragmentCreatedView(view: View, savedInstanceState: Bundle?) {
        initScreen()
    }

    override fun attachObserver() {
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(FragmentName.Appointment)
    }

    private fun initScreen(){
        fragmentAdapter = ApptTabAdapter(childFragmentManager, lifecycle)
        appt_view_pager.adapter = fragmentAdapter
        appt_tabLayout.addTab(appt_tabLayout.newTab().setText("Upcoming"))
        appt_tabLayout.addTab(appt_tabLayout.newTab().setText("Past"))
        appt_tabLayout.addTab(appt_tabLayout.newTab().setText("No Show"))
        appt_view_pager.isSaveEnabled = false

        appt_tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    appt_view_pager.currentItem = tab.position
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        appt_view_pager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                appt_tabLayout.selectTab(appt_tabLayout.getTabAt(position))
            }
        } )
    }
}