package com.taruc.foodbank

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.toObject
import com.taruc.foodbank.databinding.ActivityAdminFoodBankBinding
import com.taruc.foodbank.entity.foodBank

class admin_Activity_FoodBank : AppCompatActivity(),OnMapReadyCallback
, ActivityCompat.OnRequestPermissionsResultCallback {
    private var permissionDenied = false
    private lateinit var map:GoogleMap
    private lateinit var db: FirebaseFirestore
    private var latLng: LatLng = LatLng(0.0,0.0)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_food_bank)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?




        val foodBankName: String? = intent.getStringExtra("foodBankName")
        //binding.tvFoodBankName.text = foodBankName
        //map = binding.mapView
        db = FirebaseFirestore.getInstance()
        val rvFoodList = findViewById<RecyclerView>(R.id.rvFoodList)
        rvFoodList.layoutManager = LinearLayoutManager(this)

        val foodBankDB= db.collection("foodbanks")
        foodBankDB.whereEqualTo("name",foodBankName).get().addOnSuccessListener {
            for (document in it.documents){
                Log.d("TAG", "${document.id} => ${document.data}")
                val foodBankInstance = document.toObject<foodBank>()!!
                val foodList = foodBankInstance.foods!!
                val foodNameList = foodList.keys.toList()
                Log.d("FoodList", "$foodList")

                val tvFoodBankName = findViewById<TextView>(R.id.tvFoodBankName)
                val rvFoodList = findViewById<RecyclerView>(R.id.rvFoodList)
                val tvContact = findViewById<TextView>(R.id.tvContect)
                val tvDescri = findViewById<TextView>(R.id.tvDescri)
                val tvAddress = findViewById<TextView>(R.id.tvAddress)

                tvFoodBankName.text = foodBankInstance.name
                rvFoodList.adapter = FoodListAdapter(foodList,foodNameList)
                latLng = LatLng(foodBankInstance.lat,foodBankInstance.lng)

                mapFragment?.getMapAsync(this)
                tvContact.text = foodBankInstance.contactNumber

                tvDescri.text = foodBankInstance.description

                tvAddress.text = foodBankInstance.address


            }
        }.addOnFailureListener{
            exception ->
            Log.w("TAG", "Error getting documents: ", exception)
        }






    }


    override fun onMapReady(googleMap: GoogleMap) {


        var foodBankName: String? = intent.getStringExtra("foodBankName")
        var foodBankDB= db.collection("foodbanks")
        foodBankDB.whereEqualTo("name",foodBankName).get().addOnSuccessListener{
            for (document in it.documents){
                var foodBankInstance = document.toObject<foodBank>()!!
                latLng = LatLng(foodBankInstance.lat,foodBankInstance.lng)
            }

        }


        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true

        var location = latLng
        map.addMarker(MarkerOptions()
            .position(location)
            .title("I am here"))
       map.setMinZoomPreference(15.0f)
       map.setMaxZoomPreference(20.0f)
        map.moveCamera(CameraUpdateFactory.newLatLng(location))

    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation(){
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
            return
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) || ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            PermissionUtils.RationaleDialog.newInstance(
                LOCATION_PERMISSION_REQUEST_CODE, true
            ).show(supportFragmentManager, "dialog")
            return
        }

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
            return
        }

        if (PermissionUtils.isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) || PermissionUtils.isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation()
        } else {
            // Permission was denied. Display an error message
            // [START_EXCLUDE]
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true
            // [END_EXCLUDE]
        }
    }

    companion object {
        /**
         * Request code for location permission request.
         *
         * @see .onRequestPermissionsResult
         */
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
    override fun onResumeFragments() {
        super.onResumeFragments()
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError()
            permissionDenied = false
        }
    }
    private fun showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog.newInstance(true).show(supportFragmentManager, "dialog")
    }
}
