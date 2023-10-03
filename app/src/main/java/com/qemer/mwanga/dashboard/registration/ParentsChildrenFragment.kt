package com.qemer.mwanga.dashboard.registration

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.qemer.mwanga.api.ApiLoginClient
import com.qemer.mwanga.databinding.FragmentParentsChildrenBinding
import com.qemer.mwanga.models.ChildCreateRequest
import com.qemer.mwanga.models.ChildCreateResponse
import com.qemer.mwanga.models.GuardianRegistrationRequest
import com.qemer.mwanga.models.GuardianRegistrationResponse
import com.qemer.mwanga.models.ParentData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ParentsChildrenFragment : Fragment() {
    private var _binding: FragmentParentsChildrenBinding? = null
    private val binding get() = _binding!!

    private var parentsChildrenList =  ArrayList<ParentsChildrenModel>()
    private lateinit var parentsChildrenAdapter: ParentsChildrenAdapter
    var parentName:String = ""
    var parentId:String = ""
    var parentPhoneNo:String = ""
    var geolocation:String = ""
    var totalChildren:String = ""
    private lateinit var apiClient: ApiLoginClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentParentsChildrenBinding.inflate(inflater, container, false)
        binding.parentsChildrenTopAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        apiClient = ApiLoginClient()

        val parentData = getParentData()
        if (parentData != null) {
            parentName = parentData.parentName
            parentId = parentData.parentId
            parentPhoneNo = parentData.parentPhoneNo
            geolocation = parentData.geolocation
            totalChildren = parentData.totalChildren

            binding.tvParentName.text= parentName
            binding.tvParentId.text= parentId
            binding.tvParentPhone.text = parentPhoneNo
            binding.tvParentLocation.text = geolocation
        }

        binding.btBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btSubmit.setOnClickListener {
            val progressDialog = ProgressDialog(requireContext())
            progressDialog.setCancelable(false) // set cancelable to false
            progressDialog.setMessage("submitting..") // set message
            progressDialog.show()

            val requestData = GuardianRegistrationRequest(
                parentName,
                parentId,
                parentPhoneNo,
                totalChildren,
                "yes",
                geolocation
            )

            apiClient.getApiService(requireContext()).guardianRegistration(requestData).enqueue(object : Callback<GuardianRegistrationResponse> {
                override fun onResponse(call: Call<GuardianRegistrationResponse>, response: Response<GuardianRegistrationResponse>) {
                    if (response.isSuccessful) {
                        progressDialog.dismiss()
//                        Snackbar.make(it, "Login Successful", Snackbar.LENGTH_SHORT).show()
                        Log.e("Gideon", "onSuccess: ${response.body()!!.id}")
                        createChild(response.body()!!.id)
                    }
                }

                override fun onFailure(call: Call<GuardianRegistrationResponse>, t: Throwable) {
                    progressDialog.dismiss()
                    Snackbar.make(it, "${t.message}", Snackbar.LENGTH_SHORT).show()
                    Log.e("Gideon", "onFailure: ${t.message}")
                }
            })
        }


        parentsChildrenList.clear()
        addSampleData()
        return binding.root
    }

    private fun createChild(id: String) {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setCancelable(false)
        progressDialog.setMessage("submitting..")
        progressDialog.show()
        val receivedChildrenListString = arguments?.getString("childrenListString")

        if (receivedChildrenListString != null) {

            val cleanedString = if (receivedChildrenListString.endsWith("]")) {
                receivedChildrenListString.substring(0, receivedChildrenListString.length - 1)
            } else {
                receivedChildrenListString
            }

            val formattedString = cleanedString.trim('[', ']')

            val entries = formattedString.split("), ")

            val receivedChildrenList = ArrayList<NumberOfChildrenModel>()

            for (childModel in receivedChildrenList) {
                val requestData = ChildCreateRequest(
                    childModel.childName,
                    childModel.DOB,
                    childModel.gender,
                    childModel.delayedMilestones,
                    id,
                )


                apiClient.getApiService(requireContext()).childRegistration(requestData)
                    .enqueue(object : Callback<ChildCreateResponse> {
                        override fun onResponse(
                            call: Call<ChildCreateResponse>,
                            response: Response<ChildCreateResponse>
                        ) {
                            if (response.isSuccessful) {
                                progressDialog.dismiss()
                            }
                        }

                        override fun onFailure(call: Call<ChildCreateResponse>, t: Throwable) {
                            Log.e("Gideon", "onFailure: ${t.message}")
                        }
                    })
            }
            progressDialog.dismiss()
        }
    }

    private fun getParentData(): ParentData? {
        requireActivity().run {
            val parentName = intent.getStringExtra("parentName")
            val parentId = intent.getStringExtra("parentId")
            val parentPhoneNo = intent.getStringExtra("parentPhoneNo")
            val geolocation = intent.getStringExtra("geolocation")
            val totalChildren = intent.getStringExtra("children")

            if (parentName != null && parentId != null && parentPhoneNo != null && geolocation != null && totalChildren != null) {
                return ParentData(parentName, parentId, parentPhoneNo, geolocation, totalChildren)
            }
            return null
        }
    }


    private fun addSampleData() {
        val receivedChildrenListString = arguments?.getString("childrenListString")

        if (receivedChildrenListString != null) {

            // Remove the extra bracket at the end of the string
            val cleanedString = if (receivedChildrenListString.endsWith("]")) {
                receivedChildrenListString.substring(0, receivedChildrenListString.length - 1)
            } else {
                receivedChildrenListString
            }

            val formattedString = cleanedString.trim('[', ']')

            val entries = formattedString.split("), ")

            val receivedChildrenList = ArrayList<NumberOfChildrenModel>()

            for (entry in entries) {
                val keyValuePairs = entry.split(", ")
                val childNumber = keyValuePairs.find { it.startsWith("childNumber=") }?.split("=")?.get(1) ?: ""
                val childName = keyValuePairs.find { it.startsWith("childName=") }?.split("=")?.get(1) ?: ""
                val DOB = keyValuePairs.find { it.startsWith("DOB=") }?.split("=")?.get(1) ?: ""
                val gender = keyValuePairs.find { it.startsWith("gender=") }?.split("=")?.get(1) ?: ""
                val delayedMilestones = keyValuePairs.find { it.startsWith("delayedMilestones=") }?.split("=")?.get(1) ?: ""

                val numberOfChildrenModel = NumberOfChildrenModel(childNumber, childName, DOB, gender, delayedMilestones)
                receivedChildrenList.add(numberOfChildrenModel)
            }

            val adapter = ParentsChildrenAdapter(receivedChildrenList)

            binding.parentsChildrenRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            binding.parentsChildrenRecyclerview.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}