package com.example.myfypproject.Fragment.Profile

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.example.myfypproject.Base.ArrayInit
import com.example.myfypproject.Base.BaseFragment
import com.example.myfypproject.Dialog.GeneralDialog
import com.example.myfypproject.Model.DefaultProfileData
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import java.text.SimpleDateFormat
import java.time.Month
import java.time.Year
import java.time.YearMonth
import java.util.*
import kotlin.collections.ArrayList


class EditProfileFragment : BaseFragment(){
    private lateinit var profileData: DefaultProfileData
    private lateinit var updateProfileData: DefaultProfileData
    val userViewModel: UserViewModel by activityViewModels()
    override fun FragmentCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
     return inflater.let {
           it.inflate(R.layout.fragment_edit_profile, container, false)
        }
    }
    override fun FragmentCreatedView(view: View, savedInstanceState: Bundle?) {
        baseProgressBar(true)
        uiVisibility(edit_profile_constlayout, false)
        buttonVisibility()
        val defProfile= DefaultProfileData(accId)
        userViewModel.DefaultProfileData(defProfile)
    }
    override fun attachObserver() {
        userViewModel.ProfileDataResponse.observe(this, {
            it?.let {
                edit_profile_accEmail_edt.setText(it?.accountRegistered)
                edit_profile_nric_edt.setText(it?.nric)
                edit_profile_fullName_edt.setText(it?.fullName)
                edit_profile_phoneNumber_edt.setText(it?.phoneNumber)
                edit_profile_gender_edt.setText(it?.gender)
                edit_profile_dob_edt.setText(it?.dob)
                baseProgressBar(false)
                uiVisibility(edit_profile_constlayout, true)
                profileData = it
            }
        })
        userViewModel.relationshipVal.observe(this, {
            it?.let {
                edit_profile_gender_edt.setText(it)
            }
        })
    }
    private fun buttonVisibility(){
        edit_profile_btn.setOnClickListener {
            uiVisibility(edit_profile_btn, false)
            uiVisibility(cancel_edit_profile_btn, true)
            uiVisibility(save_edit_profile_btn, true)
            //textChangeHandler(edit_profile_fullName_edt)
            onDialogTrigger()
            editTextHandler(true)
        }
        cancel_edit_profile_btn.setOnClickListener {
            uiVisibility(edit_profile_btn, true)
            uiVisibility(cancel_edit_profile_btn, false)
            uiVisibility(save_edit_profile_btn, false)
            editTextHandler(false)
        }
        save_edit_profile_btn.setOnClickListener {
            uiVisibility(edit_profile_btn, true)
            uiVisibility(cancel_edit_profile_btn, false)
            uiVisibility(save_edit_profile_btn, false)
            uiVisibility(edit_profile_constlayout, false)
            baseProgressBar(true)
            saveProfileData(true)
            editTextHandler(false)
        }
    }
    private fun editTextHandler(isEnabled: Boolean){
        for(i in 0 until edit_profile_scrollView.childCount) {
            val view: View = edit_profile_scrollView.getChildAt(i)
            if (view is ConstraintLayout) {
                for(q in 0 until view.childCount){
                    val TxtInput: View = view.getChildAt(q)
                   if(TxtInput is TextInputLayout && q>1){
                       val edt = TxtInput.editText as TextInputEditText
                      if(isEnabled)
                      {    textChangeHandler(edt)
                      TxtInput.editText?.setTextColor(Color.parseColor("#141414"))
                      }
                       else{
                          textChangeHandler(edt)
                          onCancelHandler(edt, q)
                          TxtInput.editText?.setTextColor(Color.parseColor("#FF8E8E93"))
                      }
                       TxtInput.editText?.isEnabled = isEnabled
                   }
                }
            }
        }
    }
    private fun saveProfileData(isSave: Boolean){
        val accReg = edit_profile_accEmail_edt.text.toString()
        val nric = edit_profile_nric_edt.text.toString()
        val fullName = edit_profile_fullName_edt.text.toString()
        val phoneNumber = edit_profile_phoneNumber_edt.text.toString()
        val gender = edit_profile_gender_edt.text.toString()
        val dob = edit_profile_dob_edt.text.toString()
        updateProfileData = DefaultProfileData(
            accId,
            accReg,
            profileData.profileId, nric, fullName, phoneNumber, gender, dob
        )
        if(isSave){
            userViewModel.DefaultProfileData(updateProfileData)
            baseToastMessage("Profile updated successfully.")
        }
    }
    private fun textChangeHandler(edt: TextInputEditText){
        val txtInputLayout = edt.parent.parent as TextInputLayout
        edt.doOnTextChanged{ text, start, before, count ->
            if(text?.count() ==0){
               txtInputLayout.helperText = "required"
                save_edit_profile_btn.isEnabled=false
                save_edit_profile_btn.alpha = 0.5f
            }
            else{
                txtInputLayout.helperText =""
                save_edit_profile_btn.isEnabled=true
                save_edit_profile_btn.alpha = 1f
            }
        }
    }
    private fun onCancelHandler(edt: TextInputEditText, q: Int){
      val profileDataArray = ArrayList<String>()
        profileDataArray.add(profileData.fullName)
        profileDataArray.add(profileData.phoneNumber)
        profileDataArray.add(profileData.gender)
        profileDataArray.add(profileData.dob)
        for(i in 0 until profileDataArray.count()){
            edt.setText(profileDataArray[q - 2])
        }
        edt.clearFocus()
    }
    private fun onDialogTrigger(){
        edit_profile_gender_edt.setOnClickListener {
            childFragmentManager?.let { GeneralDialog(ArrayInit.gender,"Gender",null).show(it, "") }
        }
        edit_profile_dob_edt.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd =
                context?.let { it1 ->
                    DatePickerDialog(it1,R.style.MyDatePickerStyle, { view, year, monthOfYear, dayOfMonth ->
                        val month =monthOfYear+1
                        val dateChose = "$year-$month-$dayOfMonth"
                        edit_profile_dob_edt.setText(dateChose)
                    }, year, month, day)
                }
            dpd?.show()
        }
    }
    /*private fun dateToCalendar(date: Date): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }*/
}