package com.example.myfypproject.Fragment.Appointment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.myfypproject.Base.BaseFragment
import com.example.myfypproject.Base.DialogTitle
import com.example.myfypproject.Base.FragmentName
import com.example.myfypproject.Dialog.GeneralDialog
import com.example.myfypproject.Listener.DialogItemListener
import com.example.myfypproject.Model.*
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.AppointmentViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_add_appt.*
import kotlinx.android.synthetic.main.fragment_reschedule.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates


class AddAppointmentFragment() :BaseFragment(), View.OnClickListener, DatePickerDialog.OnDateSetListener,
    DialogItemListener {
    private val apptViewModel: AppointmentViewModel by viewModels()
    private lateinit var spHolidayList :Array<SpecialHoliday>
    private lateinit var offDay:Array<Int>
    private lateinit var selectedDate:String
    private lateinit var slotList:Array<String>
    private  var serviceList: ArrayList<String> = ArrayList()
    private lateinit var selectedTimeSlot:String
    private var serviceId by Delegates.notNull<Int>()
    private var serviceListObj:ArrayList<ServiceListResponse> = ArrayList()
    private  var specificSlotList: ArrayList<SpecificSlotListResponse> = ArrayList()
    private  var specificSlotFormatted:ArrayList<String> = ArrayList()
    private var startDate: Calendar = Calendar.getInstance() //today date
    override fun FragmentCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_appt,container,false)
    }

    override fun FragmentCreatedView(view: View, savedInstanceState: Bundle?) {
        apptViewModel.AppointmentScheduleData()
        slotList = resources.getStringArray(R.array.slots_array)
        request_appt_confirm_btn.setOnClickListener(this)
    }

    override fun attachObserver() {
        apptViewModel.apptScheduleDataResponse.observe(this, {
            initScreen()
            request_appt_date_Edt.setOnClickListener(this)
            request_appt_service_edt.setOnClickListener(this)
            spHolidayList = it.spHolidayList
            offDay = it.offDay
            for(n in it.serviceList){
                serviceList.add(n.serviceName)
            }
            for(n in it.serviceList){
                serviceListObj.add(n)
            }
        })
        apptViewModel.specificSlotListResponse.observe(this,{
            initSpecificSlot(it)
        })
        apptViewModel.requestApptResponse.observe(this,{
            baseToastMessage(it.message)
            clickViewModel.triggerBackPress()
        })
    }
    private fun initScreen(){
        request_appt_date_Edt.isFocusable = true
        request_appt_date_Edt.isEnabled = true
    }
    override fun onResume() {
        super.onResume()
        setToolBarTitle(FragmentName.AddAppointment)
    }
    private fun openDatePickerDialog(){
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month= c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)
        val dps = DatePickerDialog.newInstance(
            this,
            year, month, day
        )
        startDate.set(Calendar.DATE, day + 1)
        dps.minDate = startDate
        var maxDate = Calendar.getInstance()
        maxDate.set(Calendar.YEAR, year)
        val loopdate = startDate
        while (startDate.before(maxDate))
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
            startDate.add(Calendar.DATE, 1)
        }
        dps.setAccentColor("#149ffc")
        var disableDays = disableSpecificDaysArray()
        if(disableDays!=null){
            if(disableDays?.size!! >0){
                dps.disabledDays = disableSpecificDaysArray()
            }
        }
        activity?.supportFragmentManager?.let { it1 -> dps.show(it1, "Datepickerdialog") };
    }
    private fun openSlotDialog(){
        activity?.supportFragmentManager?.let {
            GeneralDialog(slotList, DialogTitle.Slots,this).show(
                it, ""
            )
        }
    }
    private fun initSpecificSlot(it:MutableList<SpecificSlotListResponse>){
        Log.d("specific slot", it.toString())
        specificSlotList.clear()
        specificSlotFormatted.clear()
        for(i in 0 until it.count()-1){
            specificSlotFormatted.add(it[i].slot)
        }
        for(q in it){
            specificSlotList.add(q)
        }
        if(it.isNotEmpty()){
            request_appt_time_edt.isFocusable = true
            request_appt_time_edt.isEnabled = true
            request_appt_time_edt.setOnClickListener(this)
        }
    }
    private fun openTimeSlotDialog(){
        activity?.supportFragmentManager?.let {
            GeneralDialog(specificSlotFormatted.toTypedArray(),DialogTitle.TimeSlot,this).show(
                it, ""
            )
        }
    }
    private fun openServiceDialog(){
        activity?.supportFragmentManager?.let {
            GeneralDialog(serviceList.toTypedArray(),DialogTitle.Service,this).show(
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

            startDate = dateToCalendar(date)
            val dates: MutableList<Calendar> = ArrayList()

            dates.add(startDate)
            disableDays = dates.toTypedArray()
        }
        return disableDays
    }
    private fun enableTimeSlotSelect(){
        request_appt_slot_Edt.setOnClickListener(this)
        request_appt_slot_Edt.isFocusable = true
        request_appt_slot_Edt.isEnabled = true
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
    private fun requestApptRequest(){
        val note = request_appt_note_edt.text.toString()
        val apptData = RequestAppointment(accId,selectedDate,selectedTimeSlot,serviceId,note)
        apptViewModel.RequestAppointment(apptData)

    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.reschedule_date_Edt ->
                if (offDay.size < 6) {
                    openDatePickerDialog()
                } else {
                    baseToastMessage("No work day available.")
                }
            R.id.request_appt_date_Edt -> openDatePickerDialog()
            R.id.request_appt_slot_Edt -> openSlotDialog()
            R.id.request_appt_time_edt-> openTimeSlotDialog()
            R.id.request_appt_service_edt->openServiceDialog()
            R.id.request_appt_confirm_btn->requestApptRequest()
        }
    }
    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val month = monthOfYear+1
        selectedDate= "$dayOfMonth/$month/$year"
        request_appt_date_Edt.setText(selectedDate)
        enableTimeSlotSelect()
    }
    override fun onSelectedCallBack(position: Int) {
        for(i in 0 until specificSlotList.size){
            if(i == position){
                selectedTimeSlot = specificSlotList[i].startTime
                request_appt_time_edt.setText(specificSlotList[i].slot)
            }
        }
    }
    override fun onSelectedCallBack(value: String) {
        request_appt_slot_Edt.setText(value)
        onSlotSelected(value)
    }
    override fun onGeneralSelectedCallBack(position: Int, type: String) {
        var serviceName = ""
        for(n in 0 until serviceList.count()){
            if(n== position){
                serviceName = serviceList[n]
                serviceId = serviceListObj[n].id
            }
        }
        request_appt_service_edt.setText(serviceName)
    }
}