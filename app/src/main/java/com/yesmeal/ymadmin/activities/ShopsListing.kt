package com.yesmeal.ymadmin.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.yesmeal.ymadmin.util.CusUtils
import com.yesmeal.ymadmin.R
import com.yesmeal.ymadmin.models.Shop

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class ShopsListing : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        var databaseReference = CusUtils.getDatabase().reference.child("shops")
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot?) {


                var shops = ArrayList<String>();
                shops.clear()

                for (each_shop  in p0!!.children){
                    var shop: Shop? = each_shop.getValue(Shop::class.java)
                    Log.e("SHOP_NAME",shop?.shopname)
                    shops.add(shop?.shopname!!)
                }

                var shopAdapter = ArrayAdapter(baseContext,android.R.layout.simple_spinner_item,shops)

                listView.adapter = shopAdapter

            }

            override fun onCancelled(p0: DatabaseError?) {

            }

        })



        fab.setOnClickListener { view ->

            startActivity(Intent(ShopsListing@this, NewShop::class.java))



        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
