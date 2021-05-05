package com.example.myfypproject.Base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentView(inflater,container,savedInstanceState)
    }

    abstract fun FragmentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View


}