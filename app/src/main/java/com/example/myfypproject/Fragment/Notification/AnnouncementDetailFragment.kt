package com.example.myfypproject.Fragment.Notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myfypproject.Dialog.GeneralDialog
import com.example.myfypproject.Model.NotificationListResponse
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.NotificationViewModel
import kotlinx.android.synthetic.main.fragment_announcement.*
import kotlinx.android.synthetic.main.fragment_announcement_details.*

class AnnouncementDetailFragment : Fragment() {
    companion object {
        fun newInstance(Title: String, Body:String, Date:String): AnnouncementDetailFragment {
            val fragment = AnnouncementDetailFragment()
            val args = Bundle()
            args.putString("Title", Title)
            args.putString("Body", Body)
            args.putString("Date", Date)
            fragment.arguments = args
            return fragment
        }
    }
    private val notificationViewModel: NotificationViewModel by activityViewModels()
    private  lateinit var  adapter: NotificationListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_announcement_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAnnouncementDetails()
    }

    private fun initAnnouncementDetails(){
        val args = arguments
        val Title = requireArguments().getString("Title")
        val Body = requireArguments().getString("Body")
        val Date = requireArguments().getString("Date")
        announcement_detail_title_txt.text = Title
        announcement_detail_body_txt.text = Body
        announcement_detail_date_txt.text = Date
    }
}