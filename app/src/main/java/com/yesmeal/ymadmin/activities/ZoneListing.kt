package com.yesmeal.ymadmin.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.yesmeal.ymadmin.R
import com.yesmeal.ymadmin.models.Shop
import com.yesmeal.ymadmin.models.Zone
import com.yesmeal.ymadmin.util.CusUtils


import kotlinx.android.synthetic.main.activity_zone_listing.*
import kotlinx.android.synthetic.main.content_zone_listing.*

class ZoneListing : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zone_listing)

        var databaseReference = CusUtils.getDatabase().reference.child("zones")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {


                var zones = ArrayList<String>();
                zones.clear()

                for (each_shop  in p0!!.children){
                    var zone: Zone? = each_shop.getValue(Zone::class.java)
                    zones.add(zone?.zoneName!!)
                }

                var zoneAdapter = ArrayAdapter(baseContext,android.R.layout.simple_spinner_item,zones)

                listView.adapter = zoneAdapter

            }

            override fun onCancelled(p0: DatabaseError?) {

            }

        })

        fab.setOnClickListener { view ->

            startActivity(Intent(ZoneListing@this, NewZone::class.java))



        }
    }

}
