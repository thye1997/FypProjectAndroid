package com.example.myfypproject.session

import android.content.Intent
import android.content.SharedPreferences
import android.text.BoringLayout
import com.example.myfypproject.Activity.LoginActivity
import com.example.myfypproject.Model.Account

object SessionManager {
// Constructor

    // Sharedpref file name
    private val PREF_NAME = "com.FypProjectAndroid"

    // All Shared Preferences Keys
    val IS_LOGIN = "IsLoggedIn"
    // User name (make variable public to access from outside)
    val KEY_NAME = "Name"
    // Email address (make variable public to access from outside)
    val ACC_ID = "AccID"
    val KEY_EMAIL = "Email"
    //user Name from profile
    val USER_NAME = "UserName"
    // default profile in the account
    val HAS_DEFAULT_PROFILE ="HasDefaultProfile"
    val DEFAULT_PROF_ID= "DefaultProfileId"
    // Shared Preferences
    private lateinit var pref: SharedPreferences
    // Editor for Shared preferences
    private lateinit var editor: SharedPreferences.Editor
    // Shared pref mode
    private var PRIVATE_MODE = 0


    // Shared Preferences
    private lateinit var notification_pref: SharedPreferences

    init {
        pref = ApplicationContext.getApplicationContext()
            .getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()


    }

    /**
     * Get stored session data
     */
    // user name
    // user email id
    // return user
    /*val userDetails: HashMap<String, String>
        get() {
            val text: String
            val user = HashMap<String, String>()
            user[KEY_NAME] = pref.getString(KEY_NAME, null).toString()
            user[KEY_EMAIL] = pref.getString(KEY_EMAIL, null).toString()
            user[USER_ROLE] = pref.getString(USER_ROLE, null).toString()
            return user
        }*/

    /*val AccountDetails: HashMap<String, String>
        get() {
            val text: String
            val user = HashMap<String, String>()
            user[ACC_ID] = pref.getInt(ACC_ID, 0).toString()
            user[KEY_EMAIL] = pref.getString(KEY_EMAIL, null).toString()
            user[HAS_DEFAULT_PROFILE] = pref.getString(HAS_DEFAULT_PROFILE, null).toString()
            return user
        }*/




    /**
     * Create login session
     */

    val isLogin:Boolean
    get() {
        return pref.getBoolean(IS_LOGIN,false)
    }
    val GetaccId:Int
    get() {
        return pref.getInt(ACC_ID,0)
    }
    val GetaccProfileId:Int
        get() {
            return pref.getInt(DEFAULT_PROF_ID,0)
        }
    val checkAccount: HashMap<String,Boolean>
    get() {
        val checkAccount= HashMap<String,Boolean>()
        checkAccount[IS_LOGIN] = pref.getBoolean(IS_LOGIN,false)
        checkAccount[HAS_DEFAULT_PROFILE] =pref.getBoolean(HAS_DEFAULT_PROFILE,false)
        return checkAccount
    }
    fun saveLoginDetail(accId:Int){
        editor.putInt(ACC_ID, accId)
        editor.putBoolean(IS_LOGIN, true)

        editor.commit()
    }
    fun saveHasDefaultProfileState(HasDefault:Boolean,defaultAccProfileId:Int){
        editor.putBoolean(HAS_DEFAULT_PROFILE, HasDefault)
        editor.putInt(DEFAULT_PROF_ID,defaultAccProfileId)
        editor.commit()
    }
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */

    /*fun checkLogin() {
        // Check login status
        if (!isLoggedIn) {
            // user is not logged in redirect him to Login Activity
            val i = Intent(ApplicationContext.getApplicationContext(), LoginActivity::class.java)
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            // Add new Flag to start new Activity
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            // Staring Login Activity
            ApplicationContext.getApplicationContext().startActivity(i)
        }

    }*/

     fun logoutUser() {
         // Clearing all data from Shared Preferences
         editor.clear()
         editor.commit()
         // After logout redirect user to Loing Activity
         val i = Intent(ApplicationContext.getApplicationContext(), LoginActivity::class.java)
         // Closing all the Activities
         i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
         // Add new Flag to start new Activity
         i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
         // Staring Login Activity
         ApplicationContext.getApplicationContext().startActivity(i)
     }


}