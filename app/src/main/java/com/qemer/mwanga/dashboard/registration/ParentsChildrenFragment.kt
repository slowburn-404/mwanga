package com.qemer.mwanga.dashboard.registration

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.qemer.mwanga.R
import com.qemer.mwanga.databinding.FragmentParentsChildrenBinding
import com.qemer.mwanga.models.ParentData

class ParentsChildrenFragment : Fragment() {
    private var _binding: FragmentParentsChildrenBinding? = null
    private val binding get() = _binding!!

    private var parentsChildrenList =  ArrayList<ParentsChildrenModel>()
    private lateinit var parentsChildrenAdapter: ParentsChildrenAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentParentsChildrenBinding.inflate(inflater, container, false)
        binding.parentsChildrenTopAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val parentData = getParentData()
        if (parentData != null) {
            // Now you have access to all the data in the `parentData` object
            val parentName = parentData.parentName
            val parentId = parentData.parentId
            val parentPhoneNo = parentData.parentPhoneNo
            val geolocation = parentData.geolocation
            binding.tvParentName.text= parentName
            binding.tvParentId.text= parentId
            binding.tvParentPhone.text = parentPhoneNo
            binding.tvParentLocation.text = geolocation
        }

        binding.btBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btSubmit.setOnClickListener {
            findNavController().navigate(R.id.action_parentsChildrenFragment_to_allParentsChildrenFragment)
        }

        parentsChildrenList.clear()
        addSampleData()
        return binding.root
    }

    private fun getParentData(): ParentData? {
        requireActivity().run {
            val parentName = intent.getStringExtra("parentName")
            val parentId = intent.getStringExtra("parentId")
            val parentPhoneNo = intent.getStringExtra("parentPhoneNo")
            val geolocation = intent.getStringExtra("geolocation")

            if (parentName != null && parentId != null && parentPhoneNo != null && geolocation != null) {
                return ParentData(parentName, parentId, parentPhoneNo, geolocation)
            }

            return null
        }
    }


    private fun addSampleData() {
        for (i in 1..2) {
            parentsChildrenList.add(ParentsChildrenModel("$i","Bruce Wayne", "1/1/23", "Male", "0-3 months"))
            parentsChildrenAdapter = ParentsChildrenAdapter(parentsChildrenList)
            binding.parentsChildrenRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            binding.parentsChildrenRecyclerview.setHasFixedSize(true)
            binding.parentsChildrenRecyclerview.adapter = parentsChildrenAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}