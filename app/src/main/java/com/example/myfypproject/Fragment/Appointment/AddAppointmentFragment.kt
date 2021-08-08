package com.example.myfypproject.Fragment.Appointment

import android.icu.util.TimeUnit
import android.os.Bundle
import android.text.format.DateUtils
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
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.fragment_add_appt.*
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
    val c = Calendar.getInstance()
    val day = c.get(Calendar.DAY_OF_MONTH)
    val month= c.get(Calendar.MONTH)
    val year = c.get(Calendar.YEAR)
    private var endDate: Calendar = Calendar.getInstance() //end date
    private lateinit var dps: DatePickerDialog
    override fun FragmentCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_appt, container, false)
    }

    override fun FragmentCreatedView(view: View, savedInstanceState: Bundle?) {
        uiVisibility(add_appt_rel_layout,false)
        baseProgressBar(true)
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
            for (n in it.serviceList) {
                serviceList.add(n.serviceName)
            }
            for (n in it.serviceList) {
                serviceListObj.add(n)
            }
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
            endDate.set(Calendar.YEAR, year + 1)
            dps.minDate = startDate
            dps.maxDate = endDate
            disableDays()
            uiVisibility(add_appt_rel_layout,true)
            baseProgressBar(false)
        })
        apptViewModel.specificSlotListResponse.observe(this, {
            initSpecificSlot(it)
        })
        apptViewModel.requestApptResponse.observe(this, {
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
            GeneralDialog(slotList, DialogTitle.Slots, this).show(
                it, ""
            )
        }
    }

    private fun initSpecificSlot(it: MutableList<SpecificSlotListResponse>){
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
            GeneralDialog(specificSlotFormatted.toTypedArray(), DialogTitle.TimeSlot, this).show(
                it, ""
            )
        }
    }
    private fun openServiceDialog(){
        activity?.supportFragmentManager?.let {
            GeneralDialog(serviceList.toTypedArray(), DialogTitle.Service, this).show(
                it, ""
            )
        }
    }
    private fun enableTimeSlotSelect(){
        request_appt_slot_Edt.setOnClickListener(this)
        request_appt_slot_Edt.isFocusable = true
        request_appt_slot_Edt.isEnabled = true
    }
    private fun onSlotSelected(slot: String){
        var slotVal = 0;
        for(i in 0 until  slotList.size){
            if(slotList[i]== slot){
                slotVal =i+1
            }
        }
        var specificSlot = SpecificSlot(selectedDate, slotVal)
        apptViewModel.GetSpecificSlotList(specificSlot)
    }
    private fun requestApptRequest(){
        val note = request_appt_note_edt.text.toString()
        if(::selectedDate.isInitialized && ::selectedTimeSlot.isInitialized && serviceId>0){
           val apptData = RequestAppointment(accId, selectedDate, selectedTimeSlot, serviceId, note)
            apptViewModel.RequestAppointment(apptData)
        }
        else{
            baseToastMessage("Not all required fields are filled.")
        }
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.request_appt_date_Edt ->
                if (offDay.size < 6) {
                    baseProgressBar(true)
                    openDatePickerDialog()
                    baseProgressBar(false)
                } else {
                    baseToastMessage("No work day available.")
                }
            R.id.request_appt_slot_Edt -> openSlotDialog()
            R.id.request_appt_time_edt -> openTimeSlotDialog()
            R.id.request_appt_service_edt -> openServiceDialog()
            R.id.request_appt_confirm_btn -> requestApptRequest()
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
        request_appt_time_edt.setText("")
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