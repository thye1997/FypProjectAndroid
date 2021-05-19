package com.example.myfypproject.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.FragmentManager
import com.example.myfypproject.Base.*
import com.example.myfypproject.Dialog.GeneralDialog
import com.example.myfypproject.Listener.DialogItemListener
import com.example.myfypproject.MainActivity
import com.example.myfypproject.Model.AddNewProfileRequest
import com.example.myfypproject.Model.AddProfileRequest
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.UserViewModel
import com.example.myfypproject.session.SessionManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_add_new_profile.*
import kotlinx.android.synthetic.main.activity_add_profile.*
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_edit_profile.save_edit_profile_btn
import kotlinx.android.synthetic.main.fragment_reschedule.*
import java.util.*

class AddNewProfileActivity :BaseActivity(), DialogItemListener, DatePickerDialog.OnDateSetListener {
    var nric=""
    var fullName = ""
    var phoneNumber = ""
    var relationship=""
    var gender=""
    var dob= ""
    var accId = SessionManager.GetaccId
    companion object {
        fun showAddNewProfile(activity: Activity,from:Int) {
            val intent = Intent(activity, AddNewProfileActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val extras = Bundle()
            extras.putInt(IntentKeyValue.PrevScreen, from)
            intent.putExtras(extras)
            activity.startActivity(intent)
        }
    }
    private val userViewModel :UserViewModel by viewModels()
    override var titleOfView: String ="Add New Profile"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_profile)
        editTextHandler()
        openGeneralDialog()
        attachObserver()
        submitNewProfile()
    }
    private fun attachObserver(){
        userViewModel.addNewProfileResponse.observe(this,{
            it?.let {
                if(!it.isSuccess){
                  baseToastMessage(it.message)
                }
                else{
                    baseToastMessage(it.message)
                    MainActivity.showMainActivity(this)
                }
            }
        })
        userViewModel.relationshipVal.observe(this, {
            create_relationship_edt.setText(it)
    })
    }
    private fun editTextHandler(){
        for(i in 0 until register_profile_scrollView.childCount) {
            val view: View = register_profile_scrollView.getChildAt(i)
            if (view is ConstraintLayout) {
                for(q in 0 until view.childCount){
                    val TxtInput: View = view.getChildAt(q)
                    if(TxtInput is TextInputLayout){
                        val edt = TxtInput.editText as TextInputEditText
                            textChangeHandler(edt)
                    }
                }
            }
        }
    }
    private fun textChangeHandler(edt: TextInputEditText){
        val txtInputLayout = edt.parent.parent as TextInputLayout
        edt.doOnTextChanged{ text, start, before, count ->
            checkEdtValue()
            if(text?.count() ==0){
                txtInputLayout.helperText = "required"
            }
            else{
                txtInputLayout.helperText =""
            }
        }
    }
    private fun openGeneralDialog(){
        create_gender_edt.setOnClickListener {
            GeneralDialog(ArrayInit.gender,DialogTitle.Gender,this).show(supportFragmentManager, "")
        }
        create_relationship_edt.setOnClickListener {
            GeneralDialog(RelationshipArray.relationshipArray,"Relationship",null).show(supportFragmentManager, "")
        }
        create_dob_edt.setOnClickListener {
            val c = Calendar.getInstance()
            val day = c.get(Calendar.DAY_OF_MONTH)
            val month= c.get(Calendar.MONTH)
            val year = c.get(Calendar.YEAR)
            val dps = DatePickerDialog.newInstance(
                this,
                year, month, day
            )
            dps.maxDate = c
            dps.setAccentColor("#149ffc")
            dps.show(supportFragmentManager, "Datepickerdialog")
        }
    }
    private fun submitNewProfile(){
        add_newProfile_btn.setOnClickListener{
            val addNewProfile = AddNewProfileRequest(accId,relationship,nric,fullName,phoneNumber,gender,dob)
            userViewModel.AddNewProfile(addNewProfile)
        }
    }
    private fun checkEdtValue(){
        nric = create_nric_edt.text.toString()
        fullName = create_fullName_edt.text.toString()
        phoneNumber = create_phoneNumber_edt.text.toString()
        relationship = create_relationship_edt.text.toString()
        gender = create_gender_edt.text.toString()
        dob = create_dob_edt.text.toString()
        if(nric.isEmpty() || fullName.isEmpty() || phoneNumber.isEmpty() ||relationship.isEmpty() ||gender.isEmpty() ||dob.isEmpty())
        {
            add_newProfile_btn.isEnabled=false
            add_newProfile_btn.alpha = 0.5f
        }
        else{
            add_newProfile_btn.isEnabled=true
            add_newProfile_btn.alpha = 1f
        }
    }
    override fun onSelectedCallBack(position: Int) {
    }
    override fun onSelectedCallBack(value: String) {
        create_gender_edt.setText(value)
    }
    override fun onGeneralSelectedCallBack(position: Int, type: String) {
    }
    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val month = monthOfYear+1
        dob= "$dayOfMonth/$month/$year"
        create_dob_edt.setText(dob)
    }
}