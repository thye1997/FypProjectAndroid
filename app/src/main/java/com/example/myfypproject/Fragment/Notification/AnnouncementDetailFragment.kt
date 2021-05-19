package com.example.myfypproject.Fragment.Notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myfypproject.Base.BaseFragment
import com.example.myfypproject.Dialog.GeneralDialog
import com.example.myfypproject.Model.NotificationListResponse
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.NotificationViewModel
import kotlinx.android.synthetic.main.fragment_announcement.*
import kotlinx.android.synthetic.main.fragment_announcement_details.*

class AnnouncementDetailFragment : BaseFragment() {
    override fun FragmentCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_announcement_details, container, false)
    }
    override fun FragmentCreatedView(view: View, savedInstanceState: Bundle?) {
        initAnnouncementDetails()
    }
    override fun attachObserver() {
    }
    private fun initAnnouncementDetails(){
        val title = requireArguments().getString("title").toString()
        val body = requireArguments().getString("body").toString()
        val date = requireArguments().getString("date").toString()
        announcement_detail_title_txt.text = title
        announcement_detail_body_txt.text = body
        announcement_detail_date_txt.text = date
    }
}