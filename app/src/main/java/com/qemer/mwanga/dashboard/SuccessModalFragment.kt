package com.qemer.mwanga.dashboard

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.qemer.mwanga.R
import com.qemer.mwanga.databinding.FragmentSuccessModalBinding
import android.graphics.Bitmap





class SuccessModalFragment : DialogFragment() {
    private lateinit var binding: FragmentSuccessModalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSuccessModalBinding.inflate(inflater, container, false)

        Handler(Looper.getMainLooper()).postDelayed({
            dismiss()
        }, 3000)
        return binding.root
    }

    companion object {
        const val TAG = "DialogFragment"
    }
}