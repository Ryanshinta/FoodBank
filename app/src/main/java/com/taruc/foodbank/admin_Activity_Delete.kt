package com.taruc.foodbank

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.taruc.foodbank.databinding.AdminDeleteActivityBinding
import com.taruc.foodbank.entity.foodBank

class admin_Activity_Delete : AppCompatActivity(),OnMapReadyCallback{
    private lateinit var db: FirebaseFirestore
    //private lateinit var binding:AdminDeleteActivityBinding
    private lateinit var map:GoogleMap
    private lateinit var latLng: LatLng
    private lateinit var dID:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_delete_activity)
        //binding = DataBindingUtil.setContentView(this,R.layout.admin_delete_activity)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        val foodBankName: String? = intent.getStringExtra("foodBankName")

        db = FirebaseFirestore.getInstance()
        val foodBankDB= db.collection("foodbanks")
         foodBankDB.whereEqualTo("name",foodBankName).get().addOnSuccessListener{
            for (document in it.documents){
                dID = document.id
                val foodBankObj = document.toObject<foodBank>()
                Log.d("TAG", "${document.id} => ${document.data}")
                if (foodBankObj != null) {
                    val confirmName = findViewById<TextView>(R.id.confirm_name)
                    val confirmLocation = findViewById<TextView>(R.id.confirm_location)

                    confirmName.text = foodBankObj.name.toString()
                    confirmLocation.text = foodBankObj.address.toString()
                    latLng = LatLng(foodBankObj.lat,foodBankObj.lng)
                    mapFragment?.getMapAsync(this)
                }
            }
        }

        val btDelete = findViewById<Button>(R.id.confirm_button)

        btDelete.setOnClickListener{
            db.collection("foodbanks").document(dID).delete()
            Toast.makeText(
                applicationContext,
                "Delete Successful",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        var location = latLng
        map.addMarker(
            MarkerOptions()
            .position(location)
            .title("I am here"))
        map.setMinZoomPreference(15.0f)
        map.setMaxZoomPreference(20.0f)
        map.moveCamera(CameraUpdateFactory.newLatLng(location))
    }
}