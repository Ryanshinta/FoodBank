package com.taruc.foodbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.taruc.foodbank.databinding.ActivityAdminNewFoodBankBinding

class AdminNewFoodBankActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding:ActivityAdminNewFoodBankBinding
    private lateinit var map: MapView
    private lateinit var mMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_new_food_bank)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_admin_new_food_bank)

        map = binding.mapView3
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
        mMap = p0

        mMap.setOnCameraMoveListener {
           val cur:LatLng = mMap.cameraPosition.target
            Log.i("Location",cur.toString())
        }
    }

}