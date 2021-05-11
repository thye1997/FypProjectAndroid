package com.example.myfypproject.Base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.ClickViewModel
import com.example.myfypproject.session.SessionManager

abstract class BaseFragment : Fragment() {
   protected val accId = SessionManager.GetaccId
    val clickViewModel: ClickViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentCreateView(inflater,container,savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        attachObserver()
        allowCheckIn()
        return FragmentCreatedView(view,savedInstanceState)
    }

    abstract fun FragmentCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View

    abstract fun FragmentCreatedView(view:View, savedInstanceState: Bundle?)

    abstract fun attachObserver()
    protected fun baseToastMessage(msg:String){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
    protected fun baseProgressBar(showProgress: Boolean){
        clickViewModel.SetProgressBar(showProgress)
    }
    protected fun uiVisibility(view:View,show: Boolean){
        if(show) view.visibility = View.VISIBLE
        else view.visibility = View.GONE
    }

    protected fun allowCheckIn(checkIn:Boolean = false){
        clickViewModel.SetIsCheckIn(checkIn)
    }
    protected fun setFragmentWithBackStack(fragment: Fragment, type:String)=
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(
                R.id.fragment_container,
                fragment, type
            ).addToBackStack(null)
            commit()
        }
}