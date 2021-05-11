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
import com.example.myfypproject.Base.Relationship
import com.example.myfypproject.R
import com.example.myfypproject.ViewModel.AppointmentViewModel
import com.example.myfypproject.ViewModel.UserViewModel
import com.example.myfypproject.session.ApplicationContext
import java.util.*
import kotlin.collections.ArrayList

class GeneralDialog : DialogFragment() {
    private val userViewModel: UserViewModel by activityViewModels()
    companion object {
        fun newInstance(array: Array<String>): GeneralDialog {
            val fragment = GeneralDialog()
            val args = Bundle()
             args.putStringArray("arrayss", array)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val args = arguments
        val arraylists = requireArguments().getStringArray("arrayss") as Array<String>

        /*Toast.makeText(
            ApplicationContext(), arraylists.toString(),
            Toast.LENGTH_SHORT
        ).show()*/
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            val array = resources.getStringArray(R.array.relationship_array)
            builder.setTitle("Relationship")

            builder.setSingleChoiceItems(arraylists,-1) { _, which ->
                val value = arraylists[which]
                dismiss()
                userViewModel.SetRelationshipValue(value)
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
