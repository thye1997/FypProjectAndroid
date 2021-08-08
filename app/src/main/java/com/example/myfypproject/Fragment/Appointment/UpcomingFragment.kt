package com.example.myfypproject.Fragment.Appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import com.example.myfypproject.Base.ApptType
import com.example.myfypproject.Base.BaseFragment
import com.example.myfypproject.Base.FragmentName
import com.example.myfypproject.Base.FragmentType
import com.example.myfypproject.Fragment.Notification.AnnouncementDetailFragment
import com.example.myfypproject.Model.ApptListData
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.AppointmentViewModel
import kotlinx.android.synthetic.main.fragment_announcement.*
import kotlinx.android.synthetic.main.fragment_upcoming.*
import kotlin.properties.Delegates

class UpcomingFragment(private val apptType:Int) : BaseFragment() {
    private  lateinit var  adapter: ApptListAdapter
    private val apptViewModel: AppointmentViewModel by viewModels()
    override fun FragmentCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_upcoming, container, false)
    }
    override fun FragmentCreatedView(view: View, savedInstanceState: Bundle?) {
        initApiCall()
        onScroll()
    }
    override fun attachObserver() {
        apptViewModel.isLoading.observe(this,{
            uiVisibility(appt_list_layout,!it)
            baseProgressBar(it)
        })
        apptViewModel.apptListDataResponse.observe(this,{
            it?.let {
                initAppointmentList(it)
            }
        })
    }
    private fun initAppointmentList(it:ArrayList<ApptListData>){
        if(it.isEmpty()){
            uiVisibility(upcoming_appt_empty_txt,true)
        }
        else{
            adapter = ApptListAdapter(requireActivity(),it)
            upcoming_list_listView.adapter = adapter
            initItemSelectedListener(it)
        }
    }
    private fun onScroll(){
        appt_list_swipeRefresh.setOnRefreshListener {
            initApiCall()
            appt_list_swipeRefresh.isRefreshing = false
        }
    }
    private fun initApiCall(){
        var apptListData: ApptListData?
        if(apptType == ApptType.Upcoming){
            apptListData =ApptListData(accId,0,apptStatus = ApptType.upcomingVal)
        }
        else if(apptType ==ApptType.Past){
            apptListData =ApptListData(accId,0,apptStatus = ApptType.pastVal)
        }
        else{
            apptListData =ApptListData(accId,0,apptStatus = ApptType.noShowVal)

        }
        apptViewModel.AppointmentListData(apptListData)
    }
    private fun initItemSelectedListener(it:ArrayList<ApptListData>){
        upcoming_list_listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val fragment = AppointmentDetailFragment.newInstance(
                it[position].apptId
            )
            clickViewModel.SetCurrentFragment(fragment)
            setFragmentWithBackStack(fragment,type = FragmentType.InnerFragment)
        }
    }
}