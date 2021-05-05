package com.example.myfypproject.Fragment.Notification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myfypproject.Model.NotificationListResponse
import com.example.myfypproject.R

class NotificationListAdapter(context: Context, private val itemList: ArrayList<NotificationListResponse>) : BaseAdapter(){


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

            view = inflater.inflate(R.layout.custom_notification_list, parent, false)

            holder = ViewHolder()
            holder.title = view.findViewById(R.id.announcement_title_val) as TextView
            holder.body = view.findViewById(R.id.announcement_body_val) as TextView
            holder.date = view.findViewById(R.id.annoucement_date_val) as TextView
            view.tag = holder
        }
        else{
            view = convertView
            holder = convertView.tag as ViewHolder
        }
        val title = holder.title
        val body = holder.body
        val date = holder.date

        title.text = itemList[position].title
        body.text = itemList[position].body
        date.text = itemList[position].date
        return view
    }

    private class ViewHolder {
        lateinit var title: TextView
        lateinit var body: TextView
        lateinit var date: TextView
    }

}