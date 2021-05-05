package com.example.myfypproject.Activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myfypproject.Base.BaseActivity
import com.example.myfypproject.Base.IntentKeyValue
import com.example.myfypproject.Dialog.GeneralDialog
import com.example.myfypproject.R

class AddNewProfileActivity :BaseActivity() {
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
    override var titleOfView: String ="Add New Profile"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_profile)
        //GeneralDialog.newInstance().show(supportFragmentManager,"RelationShipDialog")
    }
}