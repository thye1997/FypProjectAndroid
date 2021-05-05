package com.example.myfypproject.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleRegistry
import com.example.myfypproject.Base.BaseActivity
import com.example.myfypproject.Base.PreviousScreen
import com.example.myfypproject.MainActivity
import com.example.myfypproject.Model.LoginRequest
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.UserViewModel
import com.example.myfypproject.session.ApplicationContext
import com.example.myfypproject.session.SessionManager
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    companion object {

        val TAG = LoginActivity.javaClass.simpleName

        fun showLogin(activity: Activity) {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivity(intent)
        }
    }
    override var titleOfView: String = "Login"
    private  val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        showProgressBar(true)
        initScreen()
        getTextFieldValue()
        attachObserver()

         register_txt.setOnClickListener {
             RegisterActivity.showRegister(this)
         }
        login_btn.setOnClickListener {
            var usernameValue = login_username_edt.text.toString()
            var passwordValue = login_password_edt.text.toString()
            var loginRequest = LoginRequest(usernameValue,passwordValue)
            UIVisibility(login_view_container,false)
            userViewModel.LoginUser(loginRequest)
        }
    }
    private fun getTextFieldValue(){
        var usernameValue = login_username_edt.text.toString()
        var passwordValue = login_password_edt.text.toString()
        login_btn.background = ContextCompat.getDrawable(this, R.drawable.grey_curve_button)
        login_btn.alpha = 0.5f
        login_btn.isEnabled = false
        login_password_edt.doOnTextChanged { text, start, before, count ->
            buttonStateChange()
        }
        login_username_edt.doOnTextChanged {text, start, before, count ->
            buttonStateChange()
        }
    }
    private fun buttonStateChange(){
        var usernameValue = login_username_edt.text.toString()
        var passwordValue = login_password_edt.text.toString()
        if( usernameValue.isNotEmpty() && passwordValue.isNotEmpty()){
            login_btn.isEnabled = true
            login_btn.background= ContextCompat.getDrawable(this, R.drawable.curve_button)
            login_btn.alpha = 1f
        }
        else{
            login_btn.isEnabled = false
            login_btn.background = ContextCompat.getDrawable(this, R.drawable.grey_curve_button)
            login_btn.alpha = 0.5f
        }
    }
    private fun attachObserver(){
        userViewModel.isLoading.observe(this, {
            it?.let {showProgressBar(it)
            }
        })
        userViewModel.LoginResponse.observe(this,{
            it?.let {
                if(it?.isSuccess){
                    //baseToastMessage(it.message)
                    SessionManager.saveLoginDetail(it?.accId)//save account ID for future use
                    //baseToastMessage("Account Id=> "+it?.accId)
                    userViewModel.CheckDefaultProfile(it?.accId) //check if this account has any default profile
                }
                else{
                    UIVisibility(login_view_container,true)
                   baseToastMessage(it?.message)
                }
            }
        })
        userViewModel.DefaultProfileResponse.observe(this,{
            it?.let {
                //baseToastMessage(it.hasDefault.toString())
                UIVisibility(login_view_container,true)
                if(it?.hasDefault){
                    SessionManager.saveHasDefaultProfileState(it?.hasDefault,it?.accProfileId)
                    MainActivity.showMainActivity(this)
                }
                else{
                    SessionManager.saveHasDefaultProfileState(it?.hasDefault,0)
                    AddProfileActivity.showAddProfile(this, PreviousScreen.LoginActivity)
                }
            }
        })
    }
    private fun initScreen(){
        val isLoggedIn = SessionManager.checkAccount[SessionManager.IS_LOGIN]
        val hasDefault = SessionManager.checkAccount[SessionManager.HAS_DEFAULT_PROFILE]
        val accId = SessionManager.GetaccId
        Log.d(TAG, isLoggedIn.toString() +" "+ hasDefault.toString())
        if(isLoggedIn!!){
            userViewModel.CheckDefaultProfile(accId) //check if this account has any default profile
        }
        else{
            showProgressBar(false)
            UIVisibility(empty_login_layout, false)
            UIVisibility(login_layout,true)
        }
    }

    override fun onBackPressed() {
        shuntDownApp()
    }
}