package com.example.myfypproject.Base

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import com.example.myfypproject.R

abstract class BaseActivity : FragmentActivity() {
    lateinit var screenTitle: TextView
    lateinit var baseToolbar : Toolbar
    lateinit var baseToolbarTitle: TextView
    abstract var titleOfView : String
    lateinit var progressBar: ProgressBar
    private var layoutResID: Int = 0

    override fun setContentView(layoutResID: Int) {
        this.layoutResID = layoutResID
        val inflater = LayoutInflater.from(this)
        val rootView = inflater.inflate(R.layout.activity_base, null)
        inflater.inflate(layoutResID, rootView.findViewById(R.id.base_content) as ViewGroup)
        super.setContentView(rootView)
        bindViews()
        toolBarTitle("")
    }

    private fun bindViews() {
        //screenTitle = findViewById(R.id.baseTitle)
        baseToolbar = findViewById(R.id.base_toolbar)
        baseToolbarTitle = findViewById(R.id.toolbar_title)
        progressBar = findViewById(R.id.base_progressBar)
    }

    protected fun toolBarTitle(title: String){
        if(titleOfView.isNotEmpty() && title.isEmpty()){
            baseToolbarTitle.text = titleOfView
        }
        else if(titleOfView.isEmpty() && title.isNotEmpty()){
            baseToolbarTitle.text= title
        }
    }

    protected fun showProgressBar(show: Boolean){
        if(show){
            progressBar.visibility = View.VISIBLE;
            progressBar.bringToFront()
            getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            );
        }
        else{
            progressBar.visibility = View.GONE;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }
    protected  fun baseToastMessage(message: String){
        Toast.makeText(
            baseContext, message,
            Toast.LENGTH_SHORT
        ).show()
    }

    protected fun currentLayoutName():String{
        return resources.getResourceEntryName(this.layoutResID)

    }

    protected fun baseDialog(title: String, body: String){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Hi")
            .setPositiveButton("Ok",
                DialogInterface.OnClickListener { dialog, id ->
                })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, id ->
                    // User cancelled the dialog
                })
        // Create the AlertDialog object and return it
        builder.create()

    }
}