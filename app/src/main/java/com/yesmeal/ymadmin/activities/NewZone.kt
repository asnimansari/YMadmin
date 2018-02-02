package com.yesmeal.ymadmin.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.yesmeal.ymadmin.R
import com.yesmeal.ymadmin.models.Zone
import com.yesmeal.ymadmin.util.Constants.ZONES
import com.yesmeal.ymadmin.util.CusUtils
import kotlinx.android.synthetic.main.activity_new_zone.*

class NewZone : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_zone)



        zoneSave.setOnClickListener {

            var zoneName = zoneName.text.toString().trim()
            if (zoneName!=null && zoneName.length!=0){
                var zone = Zone(zoneName)

                val databaseReference = CusUtils.getDatabase().reference.child(ZONES)
                databaseReference.push().setValue(zone).addOnSuccessListener {
                    Toast.makeText(this@NewZone, "Saved", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@NewZone, ZoneListing::class.java))
                    finish()
                }.addOnFailureListener { Toast.makeText(this@NewZone, "Failed To Add Zone", Toast.LENGTH_SHORT).show() }

            }
            else{
                Toast.makeText(applicationContext,"Enter a Valid Zone",Toast.LENGTH_SHORT);
            }
        }

    }
}
