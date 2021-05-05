package com.example.myfypproject.Fragment.Notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.myfypproject.Fragment.Appointment.ApptListAdapter
import com.example.myfypproject.Model.Account
import com.example.myfypproject.Model.NotificationListResponse
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.AppointmentViewModel
import com.example.myfypproject.ViewModel.NotificationViewModel
import kotlinx.android.synthetic.main.fragment_announcement.*
import kotlinx.android.synthetic.main.fragment_upcoming.*

class AnnouncementFragment : Fragment() {
    private val notificationViewModel: NotificationViewModel by activityViewModels()
    private  lateinit var  adapter: NotificationListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_announcement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        notificationViewModel.NotificationList()
            attachObserver()
    }

    private fun attachObserver(){
        notificationViewModel.notificationListResponse.observe(viewLifecycleOwner, {
            it?.let {
                    initAnnouncementList(it)
            }
        })
    }

    private fun initAnnouncementList(list: ArrayList<NotificationListResponse>){
        announcement_list_listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val fragment = AnnouncementDetailFragment.newInstance(
                list[position].title,
                list[position].body,
                list[position].date
                )
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(
                    R.id.fragment_container,
                    fragment, "AD"
                )
                    commit()
            }
                //Toast.makeText(activity,list[position].body, Toast.LENGTH_LONG).show()
            }
        if(list.count()>0){
            announcement_list_listView.visibility= View.VISIBLE
            announcement_empty_txt.visibility = View.GONE
            adapter = NotificationListAdapter(requireActivity(),list)
            announcement_list_listView.adapter = adapter
        }
       else{
            announcement_list_listView.visibility = View.GONE
            announcement_empty_txt.visibility = View.VISIBLE
        }
    }
}