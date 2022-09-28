package com.taruc.foodbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.taruc.foodbank.databinding.ActivityAdminFoodBankBinding
import com.taruc.foodbank.entity.foodBank

class admin_Activity_FoodBank : AppCompatActivity(),OnMapReadyCallback {
    private lateinit var binding: ActivityAdminFoodBankBinding
    private lateinit var map:MapView
    private lateinit var mMap:GoogleMap
    private lateinit var db: FirebaseFirestore
    //private lateinit var foodBankInstance:foodBank
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_food_bank)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_admin_food_bank)
        val foodBankName: String? = intent.getStringExtra("foodBankName")
        //binding.tvFoodBankName.text = foodBankName
        map = binding.mapView
        db = FirebaseFirestore.getInstance()

        val foodBankDB= db.collection("foodbanks")
        foodBankDB.whereEqualTo("name",foodBankName).get().addOnSuccessListener {
            for (document in it.documents){
                Log.d("TAG", "${document.id} => ${document.data}")
                 val foodBankInstance = document.toObject<foodBank>()!!
                val foodList = foodBankInstance.foods

                binding.tvFoodBankName.text = foodBankInstance.name

            }
        }.addOnFailureListener{
            exception ->
            Log.w("TAG", "Error getting documents: ", exception)
        }




        map.getMapAsync(this)
        map.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onStop() {
        super.onStop()
        map.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        map.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        map.onSaveInstanceState(outState)
    }
    override fun onMapReady(p0: GoogleMap) {
        mMap = p0;
//        val location = LatLng(3.214788,101.7267968)
//        mMap.addMarker(MarkerOptions()
//            .position(location)
//            .title("Marker in Sydney"))
//        mMap.setMinZoomPreference(15.0f)
//        mMap.setMaxZoomPreference(20.0f)
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))

    }
}