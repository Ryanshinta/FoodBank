package com.taruc.foodbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.taruc.foodbank.databinding.ActivityAdminFoodBankBinding
import com.taruc.foodbank.databinding.ActivityForgotPasswordBinding

class admin_Activity_FoodBank : AppCompatActivity(),OnMapReadyCallback {
    private lateinit var binding: ActivityAdminFoodBankBinding
    private lateinit var map:MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_food_bank)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_admin_food_bank)

        map = binding.mapView

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

    }
}