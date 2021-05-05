package com.example.myfypproject.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.example.myfypproject.Base.BaseActivity
import com.example.myfypproject.Model.LoginRequest
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {

    companion object {

        val TAG = RegisterActivity.javaClass.simpleName

        fun showRegister(activity: Activity) {
            val intent = Intent(activity, RegisterActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivity(intent)
        }
    }
    override var titleOfView: String = "Register"
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        getTextFieldValue()
        attachObserver()

        register_btn.setOnClickListener {
            var usernameValue = register_username_edt.text.toString()
            var passwordValue = register_password_edt.text.toString()
            var loginRequest = LoginRequest(usernameValue,passwordValue)
            UIVisibility(register_view_container,false)
            userViewModel.RegisterUser(loginRequest)
        }
    }
    private fun getTextFieldValue(){
        var usernameValue = register_username_edt.text.toString()
        var passwordValue = register_password_edt.text.toString()
        register_btn.background = ContextCompat.getDrawable(this, R.drawable.grey_curve_button)
        register_btn.alpha = 0.5f
        register_btn.isEnabled = false
        register_password_edt.doOnTextChanged { text, start, before, count ->
            buttonStateChange(register_password_edt)
        }
        register_username_edt.doOnTextChanged {text, start, before, count ->
            buttonStateChange(register_username_edt)
        }
    }
    private fun buttonStateChange(type: TextInputEditText){
        var usernameValue = register_username_edt.text.toString()
        var passwordValue = register_password_edt.text.toString()
        if(type == register_password_edt)
        {  if(passwordValue.isNotEmpty()){
            register_pass_txtInput_layout.helperText = ""
             }
            else{
            register_pass_txtInput_layout.helperText = "required"
                }
        }
        if(type == register_username_edt){
            if(usernameValue.isNotEmpty()){
                register_Email_txtInput_layout.helperText = ""
                validateEmail(usernameValue)
            }
            else{
                register_pass_txtInput_layout.helperText = "required"
            }
        }

        if( usernameValue.isNotEmpty() && passwordValue.isNotEmpty()){
            register_btn.isEnabled = true
            register_btn.background= ContextCompat.getDrawable(this, R.drawable.curve_button)
            register_btn.alpha = 1f
            validateEmail(usernameValue)
        }
        else{
            register_btn.isEnabled = false
            register_btn.background = ContextCompat.getDrawable(this, R.drawable.grey_curve_button)
            register_btn.alpha = 0.5f
        }
    }

    private fun attachObserver(){
        userViewModel.isLoading.observe(this, {
            it?.let {showProgressBar(it)
            }
        })
        userViewModel.LoginResponse.observe(this,{
            it?.let { baseToastMessage(it.message)
                //UIVisibility(register_view_container,true)
                if(it.isSuccess){
                 LoginActivity.showLogin(this)
                }
            }
        })
    }

    private fun validateEmail(email:String){
        if( !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            register_btn.isEnabled = false
            register_btn.background= ContextCompat.getDrawable(this, R.drawable.grey_curve_button)
            register_btn.alpha = 0.5f
            register_Email_txtInput_layout.helperText = "Invalid email address"

        }
        else{
            register_Email_txtInput_layout.helperText = ""
        }
    }




}