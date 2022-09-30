package com.taruc.foodbank

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.taruc.foodbank.PermissionUtils.PermissionDeniedDialog.Companion.newInstance
import com.taruc.foodbank.PermissionUtils.isPermissionGranted
import com.taruc.foodbank.entity.foodBank
import java.io.IOException
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AdminNewFoodBankActivity : AppCompatActivity(),
    OnMapReadyCallback ,OnRequestPermissionsResultCallback,
    OnMyLocationButtonClickListener,OnMyLocationClickListener{
    private var permissionDenied = false
    private lateinit var map: GoogleMap
    private var address:String = ""
    private var latLng:LatLng = LatLng(0.0,0.0)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_new_food_bank)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        mapFragment?.getMapAsync(this)
        val db = FirebaseFirestore.getInstance()
        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        val etName = findViewById<EditText>(R.id.etFoodBankName)
        val etDesc = findViewById<EditText>(R.id.etDesciption)
        val etContact = findViewById<EditText>(R.id.etContect)
        val etAddress = findViewById<EditText>(R.id.etAddress)

        val llFoodInit = findViewById<LinearLayout>(R.id.llFoodInit)
        val lvFood = findViewById<ListView>(R.id.lvFood)
        val btAdd = findViewById<Button>(R.id.btFoodAdd)
        val etFood = findViewById<EditText>(R.id.etFood)
        val btCreate = findViewById<Button>(R.id.btNewFoodBank)

        var foodList = ArrayList<String>()
        llFoodInit.visibility = android.view.View.GONE





        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,foodList)
        lvFood.adapter = adapter

        checkBox.setOnClickListener(){
            if (checkBox.isChecked){
                llFoodInit.visibility = android.view.View.VISIBLE
                lvFood.visibility = android.view.View.VISIBLE
            }else{
                llFoodInit.visibility = android.view.View.GONE
                lvFood.visibility = android.view.View.GONE
                foodList.clear()
            }
        }

        btAdd.setOnClickListener(){

            var foodName = etFood.text.toString()
            Log.i("addFood",foodName.toString())
            foodList.add(foodName)
            adapter.notifyDataSetChanged()
        }

        btCreate.setOnClickListener(){
            var foods:Map<String,Int> = mapOf()
            for (name in foodList){
                foods = mapOf(
                    name to 1
                )
            }
            var foodBankDb = hashMapOf(
                "address" to etAddress.text.toString(),
                "contactNumber" to etContact.text.toString(),
                "description" to etDesc.text.toString(),
                "foods" to foods,
                "lat" to latLng.latitude,
                "lng" to latLng.longitude,
                "name" to etName.text.toString()
            )


            db.collection("foodbanks").document(etName.text.toString()).set(foodBankDb)
                .addOnSuccessListener {
                    Log.i("FoodBank Create","Added Successfully")
                }
                .addOnFailureListener {
                    Log.e("FoodBank Create", it.toString())
                }
            finish()
        }




    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val location = LatLng(3.00,101.55)
        map.uiSettings.isZoomControlsEnabled = true
        map.moveCamera(CameraUpdateFactory.newLatLng(location))

        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)
        enableMyLocation()
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

        if (isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) || isPermissionGranted(
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

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT)
            .show()
        return false
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(this, "Current location:\n$location", Toast.LENGTH_LONG)
            .show()

        var geocoder = Geocoder(applicationContext,Locale.getDefault())
        var addressString:String

        try {
            val addressList: kotlin.collections.List<Address> = geocoder.getFromLocation(location.latitude,location.longitude,1)
            if(addressList != null && addressList.isNotEmpty()){
                val address = addressList[0]
                val sb = StringBuilder()
                for (i in 0 until address.maxAddressLineIndex) {
                    sb.append(address.getAddressLine(i)).append("\n")
                }
                if (address.premises != null)
                    sb.append(address.premises).append(", ")

                sb.append(address.subAdminArea).append("\n")
                sb.append(address.locality).append(", ")
                sb.append(address.adminArea).append(", ")
                sb.append(address.countryName).append(", ")
                sb.append(address.postalCode)

                addressString = sb.toString()
                val etAddress = findViewById<EditText>(R.id.etAddress)
                etAddress.setText(addressString)

                latLng = LatLng(location.latitude,location.longitude)
            }
        }catch (e:IOException){
            Toast.makeText(applicationContext,"Unable connect to Geocoder",Toast.LENGTH_LONG).show()
        }



    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError()
            permissionDenied = false
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private fun showMissingPermissionError() {
        newInstance(true).show(supportFragmentManager, "dialog")
    }

}