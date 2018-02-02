package com.yesmeal.ymadmin.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.yesmeal.ymadmin.R
import kotlinx.android.synthetic.main.activity_staff_listing.*

class StaffListing : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_listing)
//        setSupportActionBar(toolbar)



        fab.setOnClickListener { view ->

            startActivity(Intent(StaffListing@this, NewStaff::class.java))



        }
    }

}
