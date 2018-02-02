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
import com.yesmeal.ymadmin.models.Staff
import com.yesmeal.ymadmin.util.CusUtils
import kotlinx.android.synthetic.main.activity_staff_listing.*
import kotlinx.android.synthetic.main.content_staff_listing.*

class StaffListing : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_listing)

        var databaseReference = CusUtils.getDatabase().reference.child("staffs")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {


                var staffs = ArrayList<String>();
                staffs.clear()

                for (each_shop  in p0!!.children){
                    var staff: Staff? = each_shop.getValue(Staff::class.java)
                    Log.e("SHOP_NAME",staff?.staffName)
                    staffs.add(staff?.staffName!!)
                }

                var staffAdapter = ArrayAdapter(baseContext,android.R.layout.simple_spinner_item,staffs)

                listView.adapter = staffAdapter

            }

            override fun onCancelled(p0: DatabaseError?) {

            }

        })



        fab.setOnClickListener { view ->

            startActivity(Intent(StaffListing@this, NewStaff::class.java))



        }
    }

}
