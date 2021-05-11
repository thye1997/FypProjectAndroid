package com.example.myfypproject.Fragment.Profile

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.myfypproject.Listener.SwitchProfileListener
import com.example.myfypproject.Model.NotificationListResponse
import com.example.myfypproject.Model.ProfileListResponse
import com.example.myfypproject.R

class SwitchProfileAdapter(private val context: Context, private val itemList: ArrayList<ProfileListResponse>, private var switchProfileListener: SwitchProfileListener) : BaseAdapter(){


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

    fun removeItem(position: Int) {
        itemList.removeAt(position)
    }
    fun sortByDefault(){
        itemList.sortBy {
            !it?.isDefault
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {

            view = inflater.inflate(R.layout.custom_switch_profile_list, parent, false)
            holder = ViewHolder()
            holder.switchProfileName = view.findViewById(R.id.switch_profile_name_val) as TextView
            holder.switchProfileRelationship = view.findViewById(R.id.switch_profile_relationship_val) as TextView
            holder.switchProfileBtn = view.findViewById(R.id.switch_profile_btn) as Button
            holder.deleteProfileBtn = view.findViewById(R.id.delete_profile_btn) as AppCompatButton
            holder.defaultProfile = view.findViewById(R.id.default_profile_val) as TextView
            view.tag = holder
        }
        else{
            view = convertView
            holder = convertView.tag as ViewHolder
        }
        val profileName = holder.switchProfileName
        val relationship = holder.switchProfileRelationship
        val switchBtn = holder.switchProfileBtn as AppCompatButton
        val deleteBtn = holder.deleteProfileBtn as AppCompatButton
        val defaultProfile = holder.defaultProfile
        profileName.text = itemList[position].fullName
        relationship.text = itemList[position].relationship
        if(itemList[position].isDefault){
            switchBtn.visibility = View.GONE
            deleteBtn.visibility = View.GONE
            defaultProfile.visibility = View.VISIBLE
        }
        else{
            switchBtn.visibility = View.VISIBLE
            deleteBtn.visibility = View.VISIBLE
            defaultProfile.visibility = View.GONE
        }
        DeleteProfile(deleteBtn, position)
        SwitchProfileDefault(switchBtn,position,itemList[position].id)
        return view
    }
    private fun DeleteProfile(deleteBtn :AppCompatButton, position: Int){
        deleteBtn.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Delete this profile?")
            //performing positive action
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                switchProfileListener.onDeleteCallBack(itemList[position].id,position)
            }
            //performing cancel action
            builder.setNegativeButton("No") { dialogInterface, which ->
                dialogInterface.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }
    fun SwitchProfileDefault(switchBtn:AppCompatButton, position: Int,idSelected:Int){
        switchBtn.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Switch to default profile?")
            //performing positive action
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                for(item in itemList){
                    item.isDefault = item.id == idSelected
                }
                switchProfileListener.onSwitchCallBack(itemList[position].id,position)
            }
            //performing cancel action
            builder.setNegativeButton("No") { dialogInterface, which ->
                dialogInterface.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }

    private class ViewHolder {
        lateinit var switchProfileName: TextView
        lateinit var switchProfileRelationship: TextView
        lateinit var switchProfileBtn: Button
        lateinit var deleteProfileBtn: Button
        lateinit var defaultProfile: TextView
    }

}