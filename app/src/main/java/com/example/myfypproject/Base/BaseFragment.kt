package com.example.myfypproject.Base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.*
import com.example.myfypproject.Fragment.Appointment.AppointmentDetailFragment
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

    protected fun setToolBarTitle(title:String){
        clickViewModel.SetFragmentTitle(title)
    }

    protected fun allowCheckIn(checkIn:Boolean = false, apptId:Int =0){
        clickViewModel.SetIsCheckIn(checkIn ,apptId)
    }

    protected inline fun <reified T> setFragmentWithBackStack( fragment: Fragment, bundle: Bundle?) where T: BaseFragment=
        run {
            clickViewModel.SetCurrentFragment(fragment)
            activity?.supportFragmentManager?.commit {
                setReorderingAllowed(true)
                replace<T>(R.id.fragment_container, tag= null,bundle)
            }
        }
    protected fun setFragmentWithBackStack(fragment: Fragment, fragmentRemove:Fragment?=null, type:String)=
        if(fragmentRemove !=null){
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(
                    R.id.fragment_container,
                    fragment, type
                ).addToBackStack(null)
                    .remove(fragmentRemove)
                commit()
            }
        }

    else{
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(
                    R.id.fragment_container,
                    fragment, type
                ).addToBackStack(null)
                setReorderingAllowed(true)
                commit()
            }
        }

}