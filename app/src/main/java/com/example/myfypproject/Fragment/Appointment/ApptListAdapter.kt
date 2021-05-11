package com.example.myfypproject.Fragment.Appointment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.myfypproject.Model.ApptListData
import com.example.myfypproject.R


class ApptListAdapter(context: Context, private val itemList: ArrayList<ApptListData>) : BaseAdapter(){


    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return itemList.size
    }

    override fun getItem(position: Int): Any {
        return itemList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(R.layout.custom_appt_list, parent, false)
            holder = ViewHolder()
            holder.username = view.findViewById(R.id.appt_username_txt) as TextView
            holder.status = view.findViewById(R.id.appt_status_val) as TextView
            holder.startTime = view.findViewById(R.id.appt_starttime_val) as TextView
            holder.endTime = view.findViewById(R.id.appt_endTime_val) as TextView
            holder.date = view.findViewById(R.id.appt_date_txt) as TextView
            holder.service = view.findViewById(R.id.appt_service_txt) as TextView
            holder.itemListView_constraint_layout = view.findViewById(R.id.custListView_constraint_layout) as ConstraintLayout
            view.tag = holder
        }
        else{
            view = convertView
            holder = convertView.tag as ViewHolder
        }
        val username = holder.username
        val status = holder.status
        val startTime = holder.startTime
        val endTime = holder.endTime
        val date = holder.date
        val service = holder.service

        username.text = itemList[position].fullName
        status.text = itemList[position].apptStatusString
        startTime.text = itemList[position].startTime
        endTime.text = itemList[position].endTime
        date.text = itemList[position].date
        service.text = itemList[position].service
        return view
    }


    private class ViewHolder {
        lateinit var username: TextView
        lateinit var status: TextView
        lateinit var service: TextView
        lateinit var startTime : TextView
        lateinit var endTime: TextView
        lateinit var date: TextView
        lateinit var itemListView_constraint_layout: ConstraintLayout
    }

}