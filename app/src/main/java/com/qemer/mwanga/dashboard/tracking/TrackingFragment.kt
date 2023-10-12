package com.qemer.mwanga.dashboard.tracking

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qemer.mwanga.databinding.FragmentTrackingBinding

class TrackingFragment : Fragment() {
    private var _binding: FragmentTrackingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTrackingBinding.inflate(inflater, container, false)

        binding.trackingFab.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(this, TrackingActivity1::class.java))
            }
        }

        return binding.root
    }
}