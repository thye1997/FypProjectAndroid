package com.example.myfypproject

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.myfypproject.Base.BaseActivity
import com.example.myfypproject.Model.Test
import com.example.myfypproject.Repository.AppointmentRepository
import com.example.myfypproject.Service.AppointmentService
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity() {
     private lateinit var apptRepository: AppointmentRepository
    override var titleOfView: String = "Home Page"

    //override var titleOfView: String ="Home Page"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(findViewById(R.id.toolbar))
        val firstFragment=FirstFragment()
        val secondFragment=SecondFragment()

        setCurrentFragment(firstFragment)
        apptRepository = AppointmentRepository()
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
             val myuser = Test("Low Chen Thye", "25")
            apptRepository.firstApiCall(myuser)
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

        }
        btmNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> setCurrentFragment(firstFragment)
                R.id.navigation_appt -> setCurrentFragment(secondFragment)
                R.id.navigation_notification -> setCurrentFragment(firstFragment)

            }
            true
        }
    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,fragment)
            commit()
        }
}