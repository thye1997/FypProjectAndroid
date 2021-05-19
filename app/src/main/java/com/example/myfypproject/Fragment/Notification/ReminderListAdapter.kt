package com.example.myfypproject.Fragment.Notification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.myfypproject.Model.NotificationListResponse
import com.example.myfypproject.Model.ReminderListReponse
import com.example.myfypproject.R

class ReminderListAdapter(context: Context, private val itemList: ArrayList<ReminderListReponse>) : BaseAdapter(){


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

            view = inflater.inflate(R.layout.fragment_custom_reminder, parent, false)

            holder = ViewHolder()
            holder.content = view.findViewById(R.id.reminder_content_txt) as TextView
            view.tag = holder
        }
        else{
            view = convertView
            holder = convertView.tag as ViewHolder
        }
        val body = holder.content

        body.text = itemList[position].content
        return view
    }

    private class ViewHolder {
        lateinit var content: TextView
    }

}