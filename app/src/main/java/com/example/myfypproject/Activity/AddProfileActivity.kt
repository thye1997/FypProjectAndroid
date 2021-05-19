package com.example.myfypproject.Activity

import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.example.myfypproject.Base.BaseActivity
import com.example.myfypproject.Base.IntentKeyValue
import com.example.myfypproject.Base.PreviousScreen
import com.example.myfypproject.Base.Relationship
import com.example.myfypproject.Dialog.GeneralDialog
import com.example.myfypproject.MainActivity
import com.example.myfypproject.Model.AddProfileRequest
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.UserViewModel
import com.example.myfypproject.session.SessionManager
import kotlinx.android.synthetic.main.activity_add_profile.*
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_login.*

class AddProfileActivity : BaseActivity() {
    companion object {

        val TAG = AddProfileActivity.javaClass.simpleName
        var inputSubmit =""

        fun showAddProfile(activity: Activity, from:Int) {
            val intent = Intent(activity, AddProfileActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val extras = Bundle()
            extras.putInt(IntentKeyValue.PrevScreen, from)
            intent.putExtras(extras)
            activity.startActivity(intent)
        }
    }
    override var titleOfView: String = "Add Profile"
    private  val userViewModel: UserViewModel by viewModels()
    var profileId:Int = 0
    var accId = SessionManager.GetaccId
    var relationship:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_profile)
        attachObserver()
        GetInputValue()
        search_profile_btn.setOnClickListener {
            val NRIC = search_NRIC_value_edt.text.toString()
            inputSubmit = relationship_input_edt.text.toString()
            userViewModel.SearchExistProfile(NRIC)
        }
        buttonStateChange("")
        relationship_input_edt.setOnClickListener {
            val arrayList = ArrayList<String>(Relationship.values().size)
            for(n in Relationship.values()){
                arrayList.add(n.name)
            }
            GeneralDialog(arrayList.toTypedArray(), "Relationship",null).show(supportFragmentManager,"RelationShipDialog")
        }
        base_toolbar.setOnMenuItemClickListener {
            if(it.itemId ==R.id.reset_txt_btn){
                it.isVisible = false
                ResetField()
            }
             true
        }
        create_newProfile_txt.setOnClickListener {
            val prevScreen = intent.getIntExtra(IntentKeyValue.PrevScreen,-1)
            AddNewProfileActivity.showAddNewProfile(this,prevScreen)
        }
        add_profile_btn.setOnClickListener {
            UIVisibility(addExist_profile_container,false)
            val addProfile= AddProfileRequest(profileId,accId,relationship)
            userViewModel.AddProfile(addProfile)
        }
    }

    private fun attachObserver(){
        userViewModel.isLoading.observe(this, {
            it?.let {showProgressBar(it)
            }
        })
        userViewModel.SearchProfileResponse.observe(this, {
            it?.let {
                //baseToastMessage("value of :"+ it?.profileId)
                if(it?.profileExist){
                    baseToastMessage("Profile Found.")
                    search_fullName_value.text = it?.fullName
                    search_phoneNum_value.text = it?.phoneNumber
                    search_gender_value.text = it?.gender
                    search_dob_value.text = it?.dob
                    search_result_layout.visibility= View.VISIBLE
                    search_profile_btn.visibility = View.GONE
                    add_profile_btn.visibility = View.VISIBLE
                    relationship_txtInputLayout.visibility = View.VISIBLE
                    create_newProfile_txt.visibility= View.GONE
                    search_NRIC_value_edt.isEnabled = false
                    profileId = it?.profileId
                    baseToolbar.inflateMenu(R.menu.menu_main)

                }
                else{
                    baseToastMessage("Profile not found.")
                    search_NRIC_value_edt.isEnabled = true
                    search_result_layout.visibility= View.GONE
                }
            }
        })
        userViewModel.relationshipVal.observe(this, {
            it?.let {
                relationship_input_edt.setText(it)
                buttonStateChange("")
                relationship = it
            }
        })
        userViewModel.GeneralResponse.observe(this, {
            it?.let {
                //UIVisibility(addExist_profile_container,true)
                if(it?.isSuccess){
                    baseToastMessage(it?.message)
                    userViewModel.CheckDefaultProfile(SessionManager.GetaccId)
                    MainActivity.showMainActivity(this)
                }
                else{
                    baseToastMessage(it?.message)
                }
            }
        })
        userViewModel.DefaultProfileResponse.observe(this,{
            it?.let {
                UIVisibility(addExist_profile_container,true)
                if(it?.hasDefault){
                    SessionManager.saveHasDefaultProfileState(true,it?.accProfileId)
                }
                else{
                    SessionManager.saveHasDefaultProfileState(true,0)
                }
            }
        })
    }
    private fun GetInputValue(){
        var nricVal: String

        search_NRIC_value_edt.doOnTextChanged { text, start, before, count ->
            nricVal = search_NRIC_value_edt.text.toString()
            buttonStateChange(nricVal)
        }
    }
    private fun buttonStateChange(value: String){
        var relationVal = relationship_input_edt.text.toString()
        var searchVal = search_NRIC_value_edt.text.toString()
        if(relationVal ==""){
            add_profile_btn.alpha = 0.5f
            add_profile_btn.background = ContextCompat.getDrawable(this, R.drawable.grey_curve_button)
            add_profile_btn.isEnabled = false
        }
        else{
            add_profile_btn.alpha = 1f
            add_profile_btn.background = ContextCompat.getDrawable(this, R.drawable.curve_button)
            add_profile_btn.isEnabled = true
        }

            if(searchVal == ""){
                search_profile_btn.alpha = 0.5f
                search_profile_btn.background = ContextCompat.getDrawable(this, R.drawable.grey_curve_button)
                search_profile_btn.isEnabled = false
            }
            else{
                search_profile_btn.alpha = 1f
                search_profile_btn.background = ContextCompat.getDrawable(this, R.drawable.curve_button)
                search_profile_btn.isEnabled = true
            }
    }
    private fun ResetField(){
        search_NRIC_value_edt.isEnabled = true
        search_NRIC_value_edt.setText("")

        search_result_layout.visibility = View.GONE
        relationship_input_edt.setText("")
        relationship_txtInputLayout.visibility = View.GONE

        add_profile_btn.visibility = View.GONE
        search_profile_btn.visibility = View.VISIBLE

        create_newProfile_txt.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        val prevScreen = intent.getIntExtra(IntentKeyValue.PrevScreen,-1)
        if(prevScreen == PreviousScreen.LoginActivity ){
            shuntDownApp()
        }
        else{
            super.onBackPressed()
        }
    }
}