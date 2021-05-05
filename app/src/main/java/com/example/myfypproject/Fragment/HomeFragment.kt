package com.example.myfypproject.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.*
import com.example.myfypproject.Model.Account
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.AppointmentViewModel
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {
    private val apptViewModel: AppointmentViewModel by activityViewModels()
    private val registry = LifecycleRegistry(this)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachObserver()
        onScroll()

    }

    private fun attachObserver(){
       apptViewModel.userDataResponse.observe(viewLifecycleOwner, Observer<Account>{
           it?.let {
           }
       })
    }
    private fun onScroll(){
        home_scrollview.setOnRefreshListener {
            Toast.makeText(
                getActivity(), "this is scrolled.",
                Toast.LENGTH_SHORT
            ).show()

            home_scrollview.isRefreshing = false
        }
    }
}