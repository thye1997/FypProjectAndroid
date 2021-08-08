package com.example.myfypproject.Fragment.Notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.myfypproject.Base.BaseFragment
import com.example.myfypproject.Model.ReminderListReponse
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.NotificationViewModel
import kotlinx.android.synthetic.main.fragment_reminder.*


class ReminderFragment : BaseFragment(){
    val notificationViewModel:NotificationViewModel by viewModels()
    private  lateinit var  adapter: ReminderListAdapter
    override fun FragmentCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_reminder, container, false)
    }
    override fun FragmentCreatedView(view: View, savedInstanceState: Bundle?) {
          notificationViewModel.ReminderList(accId)
    }

    override fun attachObserver() {
        notificationViewModel.isLoading.observe(this,{
            baseProgressBar(it)
        })
        notificationViewModel.reminderListResponse.observe(this,{
            it?.let {
                initReminderList(it)
                if(it.size==0){
                    reminder_empty_txt.visibility = View.VISIBLE
                    reminder_list_listView.visibility = View.GONE

                }
                else{
                    reminder_list_listView.visibility = View.VISIBLE
                    reminder_empty_txt.visibility = View.GONE

                }
            }
        })
    }

    private fun initReminderList(it:ArrayList<ReminderListReponse>){
        if(it.count()>0){
            uiVisibility(reminder_list_listView,true)
            uiVisibility(reminder_empty_txt,false)
            adapter = ReminderListAdapter(requireActivity(),it)
            reminder_list_listView.adapter = adapter
        }
        else{
            uiVisibility(reminder_list_listView, false)
            uiVisibility(reminder_empty_txt,true)
        }
    }

}