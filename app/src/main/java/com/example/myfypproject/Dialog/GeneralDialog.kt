package com.example.myfypproject.Dialog

import android.app.Dialog
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.ActivityNavigatorExtras
import com.example.myfypproject.Activity.AddProfileActivity
import com.example.myfypproject.Base.DialogTitle
import com.example.myfypproject.Base.Relationship
import com.example.myfypproject.Listener.DialogItemListener
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.AppointmentViewModel
import com.example.myfypproject.ViewModel.UserViewModel
import com.example.myfypproject.session.ApplicationContext
import java.util.*
import kotlin.collections.ArrayList

class GeneralDialog(private val array: Array<String>, private val title: String, private val dialogItemListener: DialogItemListener?) : DialogFragment() {
    private val userViewModel: UserViewModel by activityViewModels()
    /*companion object {
        fun newInstance(array: Array<String>, title:String): GeneralDialog {
            val fragment = GeneralDialog()
            val args = Bundle()
             args.putStringArray("arrayss", array)
            args.putString("title", title)
            fragment.arguments = args
            return fragment
        }
    }*/


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setTitle(title)
            builder.setSingleChoiceItems(array,-1) { _, which ->
                val value = array[which]
                dismiss()
                when(title){
                    DialogTitle.TimeSlot-> onTimeSlotSelected(which)
                    DialogTitle.Slots->onSelected(value)
                    DialogTitle.Service->onGeneralSelected(which,DialogTitle.Service)
                    DialogTitle.Gender->onSelected(value)
                    else->userViewModel.SetClickedValue(value)
                }
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun onTimeSlotSelected(position:Int){
            dialogItemListener?.onSelectedCallBack(position)
    }
    private fun onSelected(value:String){
        dialogItemListener?.onSelectedCallBack(value)
    }
    private fun onGeneralSelected(position: Int,type:String){
        dialogItemListener?.onGeneralSelectedCallBack(position,type)
    }
}
