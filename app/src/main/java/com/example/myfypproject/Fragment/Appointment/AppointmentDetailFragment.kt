package com.example.myfypproject.Fragment.Appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.myfypproject.Base.ApptType
import com.example.myfypproject.Base.BaseFragment
import com.example.myfypproject.Fragment.Notification.AnnouncementDetailFragment
import com.example.myfypproject.Model.ApptListData
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.AppointmentViewModel
import com.example.myfypproject.ViewModel.ClickViewModel
import kotlinx.android.synthetic.main.fragment_appt_detail.*
import kotlinx.android.synthetic.main.fragment_upcoming.*


class AppointmentDetailFragment: BaseFragment() {
    companion object {
        fun newInstance(apptId: Int): AppointmentDetailFragment {
            val fragment = AppointmentDetailFragment()
            val args = Bundle()
            args.putInt("apptId",apptId)
            fragment.arguments = args
            return fragment
        }
    }
    private val apptViewModel: AppointmentViewModel by viewModels()
    override fun FragmentCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_appt_detail, container, false)
    }

    override fun FragmentCreatedView(view: View, savedInstanceState: Bundle?) {
        val args = arguments
        val apptId = requireArguments().getInt("apptId")
        var apptListData: ApptListData?
            apptListData =ApptListData(accId,apptId,apptStatus = ApptType.upcomingVal)
        apptViewModel.AppointmentDetailData(apptListData)
    }

    override fun attachObserver() {
        apptViewModel.isLoading.observe(this,{
            uiVisibility(appt_detail_layout,!it)
            baseProgressBar(it)
        })
        apptViewModel.apptDetailDataResponse.observe(this,{
            it?.let {
                initAppointmentDetails(it)
                allowCheckIn(it[0]?.isCheckIn)
            }
        })
    }

    private fun initAppointmentDetails(it:ArrayList<ApptListData>){
        apptDetail_username_value.text = it[0]?.fullName
        apptDetail_phoneNum_value.text = it[0]?.phoneNumber
        apptDetail_gender_value.text = it[0]?.gender
        apptDetail_dob_value.text = it[0]?.dob
    }

}