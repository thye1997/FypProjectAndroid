package com.example.myfypproject.Fragment.Appointment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myfypproject.R
import kotlinx.android.synthetic.main.fragment_upcoming.*

class UpcomingFragment : Fragment() {
    companion object {
        fun newInstance(param1: String, param2: String) =
            UpcomingFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
    private  lateinit var  adapter: ApptListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       val arrayList = ArrayList<String>()
        arrayList.add("a")
        arrayList.add("b")
        adapter = ApptListAdapter(requireActivity(),arrayList)
        upcoming_list_listView.adapter = adapter
    }

}