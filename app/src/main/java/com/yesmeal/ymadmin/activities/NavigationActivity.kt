package com.yesmeal.ymadmin.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.yesmeal.ymadmin.R

import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        shops.setOnClickListener {
            startActivity(Intent(NavigationActivity@this, ShopsListing::class.java))
        }
        staffs.setOnClickListener {
            startActivity(Intent(NavigationActivity@this, StaffListing::class.java))
        }
        zones.setOnClickListener {
            startActivity(Intent(NavigationActivity@this, ZoneListing::class.java))
        }
    }
}
