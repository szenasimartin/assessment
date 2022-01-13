package com.szenamartin.android.vehicle.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.ClusterManager.OnClusterItemClickListener
import com.szenamartin.android.vehicle.R
import com.szenamartin.android.vehicle.base.BaseActivity
import com.szenamartin.android.vehicle.base.BaseViewModel
import com.szenamartin.android.vehicle.map.clustering.MyItem
import com.szenamartin.android.vehicle.utils.showBottomSheet
import kotlinx.android.synthetic.main.activity_map.progressBar
import kotlinx.android.synthetic.main.item_vehicle.model_value


class MapActivity : BaseActivity<MapViewModel>(), OnMapReadyCallback {
    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 11
        private const val TAG = "MapActivity"
        private const val DEFAULT_ZOOM = 10f
    }

    private var lastKnownLocation: Location? = null
    private lateinit var clusterManager: ClusterManager<MyItem>
    private var locationPermissionGranted = false
    private var map: GoogleMap? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null

    override fun getViewModelClass(): Class<MapViewModel> {
        return MapViewModel::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        viewModel.getScreenState().observe(this, {
            if (it == BaseViewModel.ScreenState.LOADING) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        viewModel.movieList.observe(this, { vehicles ->
            clusterManager = ClusterManager(this, googleMap)
            googleMap.setOnCameraIdleListener(clusterManager)
            googleMap.setOnMarkerClickListener(clusterManager)
            vehicles.forEachIndexed { index, item ->
                clusterManager.addItem(MyItem(item.latitude, item.longitude, item.model, item.state, item.resolution, item.vehicleId))
            }
            clusterManager.cluster()
            clusterManager.setOnClusterItemClickListener { item ->
                showBottomSheet(this, item)
                false
            }
        })
        map = googleMap
        updateLocationUI()
        getDeviceLocation()
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationUI() {
        if (map == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                fusedLocationProviderClient?.let { fusedLocationProviderClient ->
                    val locationResult = fusedLocationProviderClient.lastLocation
                    locationResult.addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            lastKnownLocation = task.result
                            if (lastKnownLocation != null) {
                                map?.moveCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        LatLng(
                                            lastKnownLocation!!.latitude,
                                            lastKnownLocation!!.longitude
                                        ), DEFAULT_ZOOM
                                    )
                                )
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.")
                            Log.e(TAG, "Exception: %s", task.exception)
                            val defaultLocation = LatLng(47.497913, 19.040236)
                            map?.moveCamera(
                                CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM)
                            )
                            map?.uiSettings?.isMyLocationButtonEnabled = false
                        }
                    }
                }

            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }
}
