package com.qemer.mwanga.dashboard.addchild

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.qemer.mwanga.R
import com.qemer.mwanga.api.ApiLoginClient
import com.qemer.mwanga.dashboard.SuccessModalFragment
import com.qemer.mwanga.dashboard.programs.ProgramsListAdapter
import com.qemer.mwanga.databinding.FragmentDetailsBinding
import com.qemer.mwanga.models.GetChildrenResponse
import com.qemer.mwanga.models.GetGuardianDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var apiClient: ApiLoginClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        apiClient = ApiLoginClient()
        binding.detailsTopAppBar.setNavigationOnClickListener {
            requireActivity().finish()
        }
        binding.detailsFab.setOnClickListener {
            findNavController().navigate(R.id.action_detailsFragment_to_addNewChildFragment)
        }
        val sharedPreferences = requireContext().getSharedPreferences("MwangaPrefs", Context.MODE_PRIVATE)
        val parentId = sharedPreferences.getString("parentId", null)
        if (parentId != null) {
            Log.d("parentId", parentId)
        }
        getDetails(parentId)


        return binding.root
    }

    private fun getDetails(parentId: String?) {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()

        apiClient.getApiService(requireContext()).getGuardianDetails(parentId).enqueue(object :
            Callback<GetGuardianDetails> {
            override fun onResponse(call: Call<GetGuardianDetails>, response: Response<GetGuardianDetails>) {
                Log.d(SuccessModalFragment.TAG, "responseData" + response.body())
                if (response.isSuccessful) {
                    progressDialog.dismiss()
                    Log.d("Angela", "${response.body()}")
                    binding.parentName.text = response.body()!!.parentName
                    binding.parentsId.text = response.body()!!.nationalId
                    binding.isEligible.text = response.body()!!.isEligible
                } else{
                    progressDialog.dismiss()
                    Snackbar.make(requireView(), "Failed to get data, Retry!!", Snackbar.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetGuardianDetails>, t: Throwable) {
                Log.e("Angela", "onFailure: ${t.message}")
                progressDialog.dismiss()
                Snackbar.make(requireView(), "${t.message}", Snackbar.LENGTH_SHORT).show()
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}