package com.example.myfypproject.Fragment.Appointment

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.myfypproject.Base.*
import com.example.myfypproject.Dialog.GeneralDialog
import com.example.myfypproject.Listener.DialogItemListener
import com.example.myfypproject.Model.RescheduleAppointment
import com.example.myfypproject.Model.SpecialHoliday
import com.example.myfypproject.Model.SpecificSlot
import com.example.myfypproject.Model.SpecificSlotListResponse
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.AppointmentViewModel
import com.example.myfypproject.ViewModel.ClickViewModel
import com.example.myfypproject.ViewModel.UserViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_add_appt.*
import kotlinx.android.synthetic.main.fragment_reschedule.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates


class RescheduleFragment(): BaseFragment(), View.OnClickListener, DatePickerDialog.OnDateSetListener, DialogItemListener{
    private var apptId by Delegates.notNull<Int>()
    private val apptViewModel: AppointmentViewModel by viewModels()
    private lateinit var spHolidayList :Array<SpecialHoliday>
    private lateinit var offDay:Array<Int>
    private lateinit var selectedDate:String
    private lateinit var slotList:Array<String>
    private lateinit var selectedTimeSlot:String
    private  var specificSlotList: ArrayList<SpecificSlotListResponse> = ArrayList()
    private  var specificSlotFormatted:ArrayList<String> = ArrayList()
    private var startDate: Calendar = Calendar.getInstance() //today date
    private var endDate: Calendar = Calendar.getInstance() //end date
    val c = Calendar.getInstance()
    val day = c.get(Calendar.DAY_OF_MONTH)
    val month= c.get(Calendar.MONTH)
    val year = c.get(Calendar.YEAR)
    private lateinit var dps: DatePickerDialog
    override fun FragmentCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_reschedule, container, false)
    }

    override fun FragmentCreatedView(view: View, savedInstanceState: Bundle?) {
        uiVisibility(resch_appt_rel_layout,false)
        baseProgressBar(true)
        apptViewModel.AppointmentScheduleData()
        slotList = resources.getStringArray(R.array.slots_array)
        apptId = requireArguments().getInt("apptId")
        clickViewModel.SetIsCheckIn(false, apptId)
        reschedule_confirm_btn.setOnClickListener(this)
    }

    override fun attachObserver() {
        apptViewModel.apptScheduleDataResponse.observe(this, {
            initScreen()
            reschedule_date_Edt.setOnClickListener(this)
            spHolidayList = it.spHolidayList
            offDay = it.offDay

            dps = DatePickerDialog.newInstance(
                this,
                year, month, day
            )
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val getCurrentDateTime: String = sdf.format(Date())
            var skipDate = 0
            for(date in spHolidayList){
                val nextDay = SimpleDateFormat("dd/MM/yyyy").parse(date.date)

                val today =SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateTime)
                val dateDiff = java.util.concurrent.TimeUnit.DAYS.convert(nextDay.time - today.time, java.util.concurrent.TimeUnit.MILLISECONDS).toInt()
                if(dateDiff ==1 || (dateDiff in 2..0) ){
                    skipDate ++
                }
            }
            startDate.set(Calendar.DATE, day+ skipDate)
            endDate.set(Calendar.YEAR, year+1)
            dps.minDate = startDate
            dps.maxDate = endDate
            disableDays()
            uiVisibility(resch_appt_rel_layout,true)
            baseProgressBar(false)
        })
        apptViewModel.specificSlotListResponse.observe(this,{
               initSpecificSlot(it)
        })
        apptViewModel.rescheduleAppointmentResponse.observe(this,{
            if(it.isSuccess){
                baseToastMessage(it.message)
                clickViewModel.triggerBackPress()
            }
        })
    }
    private fun initScreen(){
        reschedule_date_Edt.isFocusable = true
        reschedule_date_Edt.isEnabled = true
    }

    private fun disableDays(){
        var maxDate = Calendar.getInstance()
        //var disableDays = disableSpecificDaysArray()
        maxDate.set(Calendar.YEAR, year)
        val loopdate = startDate
        var oneYear = 365
        while (oneYear >0)
        {
            val dayOfWeek = loopdate.get(Calendar.DAY_OF_WEEK)
            var date:Date?
            if(offDay!=null){
                for(i in 0 until offDay.size){
                    if(dayOfWeek == offDay[i]+1){
                        val dates: ArrayList<Calendar> = ArrayList()
                        dates.add(loopdate)
                        dps.disabledDays = dates.toTypedArray()
                    }
                }
            }
            if(spHolidayList!=null) {
                val specificDate: ArrayList<Calendar> = ArrayList()
                for (n in spHolidayList) {
                    val sdf = SimpleDateFormat("dd/MM/yyyy")
                    val currentDate = sdf.format(loopdate.time)
                    val currDate =SimpleDateFormat("dd/MM/yyyy").parse(currentDate)
                    val nextDay = SimpleDateFormat("dd/MM/yyyy").parse(n.date)
                    val dateDiff = java.util.concurrent.TimeUnit.DAYS.convert(nextDay.time - currDate.time, java.util.concurrent.TimeUnit.MILLISECONDS).toInt()
                    if (dateDiff ==0) {
                        specificDate.add(loopdate)
                        dps.disabledDays = specificDate.toTypedArray()
                    }
                }
            }
            startDate.add(Calendar.DATE, 1)
            oneYear-=1
        }
    }
    private fun openDatePickerDialog(){
        dps.setAccentColor("#149ffc")
        activity?.supportFragmentManager?.let { it1 -> dps.show(it1, "Datepickerdialog") };
    }
    private fun openSlotDialog(){
        activity?.supportFragmentManager?.let {
            GeneralDialog(slotList,DialogTitle.Slots,this).show(
                it, ""
            )
        }
    }
    private fun dateToCalendar(date: Date): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }
    private fun disableSpecificDaysArray():Array<Calendar>?{
        var date: Date? = null
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        var disableDays:Array<Calendar>? = null
        for (i in 0 until spHolidayList.count()) {
            date = sdf.parse(spHolidayList[i].date)
            baseToastMessage(date.toString())
            startDate = dateToCalendar(date)
            val dates: MutableList<Calendar> = ArrayList()

            dates.add(startDate)
            disableDays = dates.toTypedArray()
        }
        return disableDays
    }
    private fun enableTimeSlotSelect(){
        reschedule_slot_Edt.setOnClickListener(this)
        reschedule_slot_Edt.isFocusable = true
        reschedule_slot_Edt.isEnabled = true
    }
    private fun onSlotSelected(slot:String){
        var slotVal = 0;
        for(i in 0 until  slotList.size){
            if(slotList[i]== slot){
                slotVal =i+1
            }
        }
        var specificSlot = SpecificSlot(selectedDate,slotVal)
        apptViewModel.GetSpecificSlotList(specificSlot)

    }
    private fun initSpecificSlot(it:MutableList<SpecificSlotListResponse>){
        specificSlotList.clear()
        specificSlotFormatted.clear()
        for(i in 0 until it.count()-1){
            specificSlotFormatted.add(it[i].slot)
        }
        for(q in it){
            specificSlotList.add(q)
        }
        if(it.isNotEmpty()){
            reschedule_time_edt.isFocusable = true
            reschedule_time_edt.isEnabled = true
            reschedule_time_edt.setOnClickListener(this)
        }
    }
    private fun openTimeSlotDialog(){
        activity?.supportFragmentManager?.let {
            GeneralDialog(specificSlotFormatted.toTypedArray(),DialogTitle.TimeSlot,this).show(
                it, ""
            )
        }
    }
    private fun rescheduleRequest(){
        if(::selectedDate.isInitialized && ::selectedTimeSlot.isInitialized ) {
            val rescheduleAppt = RescheduleAppointment(
                ApptActionType.reschedule,
                apptId,
                selectedDate,
                selectedTimeSlot
            )
            apptViewModel.RescheduleAppointment(rescheduleAppt)
        }
        else{
            baseToastMessage("Not all required fields are filled.")
        }
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.reschedule_date_Edt ->
                if (offDay.size < 6) {
                    openDatePickerDialog()
                } else {
                    baseToastMessage("No work day available.")
                }
            R.id.reschedule_slot_Edt -> openSlotDialog()
            R.id.reschedule_time_edt -> openTimeSlotDialog()
            R.id.reschedule_confirm_btn->rescheduleRequest()
        }
    }
    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val month = monthOfYear+1
         selectedDate= "$dayOfMonth/$month/$year"
        reschedule_date_Edt.setText(selectedDate)
        enableTimeSlotSelect()
    }
    override fun onSelectedCallBack(position: Int) {
        for(i in 0 until specificSlotList.size){
            if(i == position){
                selectedTimeSlot = specificSlotList[i].startTime
                reschedule_time_edt.setText(specificSlotList[i].slot)
            }
        }
    }
    override fun onSelectedCallBack(value: String) {
        reschedule_slot_Edt.setText(value)
        onSlotSelected(value)
    }

    override fun onGeneralSelectedCallBack(position: Int, type: String) {
    }

}