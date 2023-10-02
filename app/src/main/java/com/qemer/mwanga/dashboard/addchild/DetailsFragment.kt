package com.qemer.mwanga.dashboard.addchild

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.qemer.mwanga.R
import com.qemer.mwanga.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        binding.detailsTopAppBar.setNavigationOnClickListener {
            requireActivity().finish()
        }
        binding.detailsFab.setOnClickListener {
            findNavController().navigate(R.id.action_detailsFragment_to_addNewChildFragment)
        }


        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}