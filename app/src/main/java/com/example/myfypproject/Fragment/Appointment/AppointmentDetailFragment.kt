package com.example.myfypproject.Fragment.Appointment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.myfypproject.Base.*
import com.example.myfypproject.Fragment.Notification.AnnouncementDetailFragment
import com.example.myfypproject.Model.ApptListData
import com.example.myfypproject.Model.RescheduleAppointment
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.AppointmentViewModel
import com.example.myfypproject.ViewModel.ClickViewModel
import com.example.myfypproject.session.SessionManager
import kotlinx.android.synthetic.main.fragment_appt_detail.*
import kotlinx.android.synthetic.main.fragment_upcoming.*
import kotlin.properties.Delegates


class AppointmentDetailFragment: BaseFragment(),View.OnClickListener {
    private var apptIds by Delegates.notNull<Int>()
   private  lateinit var apptListData: ApptListData
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
        apptIds = requireArguments().getInt("apptId")
            apptListData =ApptListData(accId,apptIds,apptStatus = ApptType.upcomingVal)
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
                allowCheckIn(it[0]?.isCheckIn, apptIds)
            }
        })
        apptViewModel.rescheduleAppointmentResponse.observe(this,{
            baseToastMessage(it.message)
            apptViewModel.AppointmentDetailData(apptListData)
        })
    }

    private fun initAppointmentDetails(it:ArrayList<ApptListData>){
        apptDetail_username_value.text = it[0]?.fullName
        apptDetail_phoneNum_value.text = it[0]?.phoneNumber
        apptDetail_gender_value.text = it[0]?.gender
        apptDetail_dob_value.text = it[0]?.dob
        apptDetail_date_value.text = it[0]?.date
        apptDetail_slot_value.text = it[0]?.slot
        apptDetail_service_value.text = it[0]?.service
        apptDetail_note_value.text = it[0]?.note
        apptDetail_status_value.text = it[0]?.apptStatusString
        appt_detail_cancel_btn.setOnClickListener(this)
        appt_detail_reschedule_btn.setOnClickListener(this)

        buttonVisibility(it[0].isAction)
    }

    override fun onClick(v: View?) {
        when(v?.id){
           R.id.appt_detail_cancel_btn-> changeApptState(ApptAction.Cancel)
           R.id.appt_detail_reschedule_btn -> changeApptState(ApptAction.Reschedule)
        }
    }

    private fun buttonVisibility(isVisible:Boolean){
        if(!isVisible){
            appt_detail_reschedule_btn.visibility = View.GONE
            appt_detail_cancel_btn.visibility = View.GONE
        }
        else{
            appt_detail_reschedule_btn.visibility = View.VISIBLE
            appt_detail_cancel_btn.visibility = View.VISIBLE
        }
    }

    private fun changeApptState(state:Int){
       if(state == ApptAction.Reschedule){
           val bundle = bundleOf("apptId" to apptIds)
           setFragmentWithBackStack<RescheduleFragment>(RescheduleFragment(), bundle)
       }
        else{
           val builder = AlertDialog.Builder(context)

           builder.setMessage("Do you want to cancel appointment?")

           //performing positive action
           builder.setPositiveButton("Yes") { dialogInterface, which ->
               val rescheduleAppt = RescheduleAppointment( ApptActionType.cancel,apptIds,"","")
               apptViewModel.RescheduleAppointment(rescheduleAppt)           }
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

}