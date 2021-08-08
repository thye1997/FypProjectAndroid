package com.example.myfypproject.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.myfypproject.Base.BaseFragment
import com.example.myfypproject.Base.FragmentName
import com.example.myfypproject.Base.FragmentType
import com.example.myfypproject.Fragment.Appointment.AddAppointmentFragment
import com.example.myfypproject.Model.DashboardDataCount
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.AppointmentViewModel
import com.example.myfypproject.ViewModel.DashboardViewModel
import kotlinx.android.synthetic.main.fragment_first.*
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : BaseFragment()  {
    private val apptViewModel: AppointmentViewModel by activityViewModels()
    private val dashboardViewModel: DashboardViewModel by viewModels()
    override fun FragmentCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }
    override fun FragmentCreatedView(view: View, savedInstanceState: Bundle?) {
        onScroll()
        onAddApptClick()
        dashboardViewModel.DashboardData(accId)
    }
    override fun attachObserver() {
        dashboardViewModel.dashboardDataResponse.observe(this,{
            it?.let {
                populateDashboard(it)
            }
        })
    }
    override fun onResume() {
        super.onResume()
        setToolBarTitle(FragmentName.Home)
    }

    private fun onScroll(){
        home_scrollview.setOnRefreshListener {
            home_scrollview.isRefreshing = false
        }
    }
    private fun onAddApptClick(){
        add_appt_btn.setOnClickListener{
            setFragmentWithBackStack<AddAppointmentFragment>(AddAppointmentFragment(), null)
        }
    }

    private fun populateDashboard(it:DashboardDataCount){
        upcoming_value.text = it.upcomingCount.toString()
        past_value.text = it.pastCount.toString()
        noShow_value.text = it.noShowCount.toString()
        if(it?.apptData!=null){
            uiVisibility(no_active_label,false)
            uiVisibility(active_appt_layout, true)
            active_appt_username_txt.text = it?.apptData.fullName
            active_appt_date_txt.text = it?.apptData.date
            active_appt_service_txt.text = it?.apptData.service
            active_appt_starttime_val.text = it?.apptData.startTime
            active_appt_endTime_val.text =it?.apptData.endTime
            active_appt_status_val.text = it?.apptData.apptStatusString
        }
        else{
            uiVisibility(active_appt_layout,false)
            uiVisibility(no_active_label,true)
        }

    }
}