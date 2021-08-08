package com.example.myfypproject.Base

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.example.myfypproject.R
import com.example.myfypproject.session.ApplicationContext
import com.example.myfypproject.session.SessionManager
import kotlinx.android.synthetic.main.activity_login.*

abstract class BaseActivity : AppCompatActivity() {
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
        ApplicationContext.setApplicationContext(this)
        bindViews()
        toolBarTitle("")
    }

    private fun bindViews() {
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
    protected fun UIVisibility(view:View, isShow:Boolean){
        if(!isShow) view.visibility = View.GONE
        else view.visibility = View.VISIBLE
    }
    protected fun showProgressBar(show: Boolean){
        if(show){
            progressBar.visibility = View.VISIBLE;
            progressBar.bringToFront()
            getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
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

    var lastPress: Long = 0
    var backpressToast: Toast? = null
    protected fun shuntDownApp() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastPress > 5000) {
            backpressToast = Toast.makeText(baseContext, "Press back again to exit", Toast.LENGTH_LONG)
            backpressToast!!.show()
            lastPress = currentTime
        }
        else {
            if (backpressToast != null) backpressToast!!.cancel()
            super.onBackPressed()
            ActivityCompat.finishAffinity(this)// close app immediately
        }
    }

}