package com.yesmeal.ymadmin.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.yesmeal.ymadmin.R
import com.yesmeal.ymadmin.models.Staff
import com.yesmeal.ymadmin.util.Constants
import com.yesmeal.ymadmin.util.CusUtils
import kotlinx.android.synthetic.main.activity_new_staff.*

class NewStaff : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_staff)
        saveStaff.setOnClickListener {


            var staffName = staffName.text.toString().trim()
            var staffMobile = staffMobile.text.toString().trim()
            var staffEmail = staffEmail.text.toString().trim()

            if (staffName!=null && staffMobile!=null && staffMobile.length==10 && staffName.length!=0){

                var staff = Staff(staffName,staffMobile,staffEmail)

                val databaseReference = CusUtils.getDatabase().reference.child(Constants.STAFFS)
                databaseReference.push().setValue(staff).addOnSuccessListener {
                    Toast.makeText(this@NewStaff, "Saved", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@NewStaff, StaffListing::class.java))
                    finish()
                }.addOnFailureListener { Toast.makeText(this@NewStaff, "Failed To Add Staff", Toast.LENGTH_SHORT).show() }


            }
            else{
                Toast.makeText(applicationContext,"Please Enter StaffName and Mobile Numbe",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
