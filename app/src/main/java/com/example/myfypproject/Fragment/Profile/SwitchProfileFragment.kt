package com.example.myfypproject.Fragment.Profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleRegistry
import com.example.myfypproject.Activity.AddProfileActivity
import com.example.myfypproject.Base.BaseFragment
import com.example.myfypproject.Base.PreviousScreen
import com.example.myfypproject.Fragment.Notification.NotificationListAdapter
import com.example.myfypproject.Listener.SwitchProfileListener
import com.example.myfypproject.Model.ProfileListResponse
import com.example.myfypproject.Model.SwitchProfileRequest
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.AppointmentViewModel
import com.example.myfypproject.ViewModel.UserViewModel
import com.example.myfypproject.session.SessionManager
import kotlinx.android.synthetic.main.fragment_switch_profile.*


class SwitchProfileFragment : BaseFragment(),SwitchProfileListener {
    private val userViewModel: UserViewModel by viewModels()
    private  lateinit var  adapter: SwitchProfileAdapter
    private var profileList: ArrayList<ProfileListResponse> = ArrayList()
    override fun FragmentCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_switch_profile, container, false)
    }

    override fun FragmentCreatedView(view: View, savedInstanceState: Bundle?) {
        initScreen()
        userViewModel.GetProfileList(accId)

    }

    override fun attachObserver(){
          userViewModel.ProfileListResponse.observe(viewLifecycleOwner,{
              it?.let {
                  initProfileList(it)
              }
          })
    }
    override fun onResume() {
        super.onResume()
        //Toast.makeText(activity,"fragment resumed",Toast.LENGTH_SHORT).show()
    }
    private fun initProfileList(it: ArrayList<ProfileListResponse>)
    {
        adapter = SwitchProfileAdapter(requireActivity(),it,this)
        switch_profile_listView.adapter = adapter
        profileList = it
        adapter.sortByDefault()
        adapter.notifyDataSetChanged()
    }
    private fun initScreen(){
        add_profile_floatBtn.setOnClickListener {
            activity?.let{
                val intent = Intent (it, AddProfileActivity::class.java)
                it.startActivity(intent)
                AddProfileActivity.showAddProfile(it, PreviousScreen.SwitchProfile)
            }
        }
    }
    override fun onDeleteCallBack(Id: Int, position: Int) {
        userViewModel.DeleteProfile(Id)
        adapter.removeItem(position)
        adapter.notifyDataSetChanged()
        userViewModel.DeleteProfileResponse.observe(this,{
            it?.let {
                if(it?.isSuccess){
                    Toast.makeText(context,it?.message,Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    override fun onSwitchCallBack(Id: Int, position: Int) {
        val switchProfileRequest = SwitchProfileRequest(accId,Id)
        userViewModel.SwitchDefaultProfile(switchProfileRequest)
        userViewModel.SwitchDefaultProfileResponse.observe(viewLifecycleOwner,{
            it?.let {
                if(it?.isSuccess){
                    Toast.makeText(context,it?.message,Toast.LENGTH_SHORT).show()
                    adapter.sortByDefault()
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }
}