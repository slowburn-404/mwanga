package com.qemer.mwanga.dashboard.registration

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.textfield.TextInputLayout
import com.qemer.mwanga.databinding.DialogGpsBinding
import com.qemer.mwanga.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    var long: String? = null
    var lat: String? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        val rootView = binding.root
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        if (isGpsEnabled()) {
            if (isLocationPermissionGranted()) {
                getCurrentLocation()
            }
        } else {
            showGpsDisabledDialog()
        }

        binding.btSubmit.setOnClickListener {
            if (validateFields()) {
                val parentName = binding.parentName.editText?.text?.trim().toString()
                val parentId = binding.parentsId.editText?.text?.trim().toString()
                val parentPhoneNo = binding.parentsPhoneNumber.editText?.text?.trim().toString()
                val geolocation = "$lat, $long"
                val numberOfChildren = binding.numberOfChildren.editText?.text?.trim().toString()
                requireActivity().run {

                    val intent = Intent(this, RegisterParentActivity::class.java)
                    intent.putExtra("parentName", parentName)
                    intent.putExtra("parentId", parentId)
                    intent.putExtra("parentPhoneNo", parentPhoneNo)
                    intent.putExtra("geolocation", geolocation)
                    intent.putExtra("children", numberOfChildren)
                    startActivity(intent)
                }

            } else {

            }
        }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateFields(): Boolean {
        val parentName = validateField(binding.parentName, "Parent's Name is required")
        val parentsId = validateField(binding.parentsId, "Parent's ID is required")
        val parentsPhoneNumber =
            validateField(binding.parentsPhoneNumber, "Parent's Phone Number is required")
        val numberOfChildren =
            validateField(binding.numberOfChildren, "Number of Children is required")

        return parentName.isNotEmpty() && parentsId.isNotEmpty() && parentsPhoneNumber.isNotEmpty() && numberOfChildren.isNotEmpty()
    }

    private fun validateField(textInputLayout: TextInputLayout, errorMessage: String): String {
        val text = textInputLayout.editText?.text.toString().trim()

        if (text.isEmpty()) {
            textInputLayout.error = errorMessage
        } else {
            textInputLayout.error = null
        }

        return text
    }

    private fun isGpsEnabled(): Boolean {
        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
    private fun showGpsDisabledDialog() {
        val dialogBinding = DialogGpsBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        dialogBinding.btCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.enable.setOnClickListener {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        } else {
            Toast.makeText(requireActivity(), "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                val location = task.result
                if (location != null) {
                    lat = location.latitude.toString()
                    long = location.longitude.toString()
                } else {
                    val locationRequest = LocationRequest()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(10000)
                        .setFastestInterval(1000)
                        .setNumUpdates(1)

                    val locationCallback = object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            val location = locationResult.lastLocation
                            lat = location!!.latitude.toString()
                            long = location.longitude.toString()
                        }
                    }

                    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
                }
            }
        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        return (ActivityCompat.checkSelfPermission(requireActivity(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(), ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }
}