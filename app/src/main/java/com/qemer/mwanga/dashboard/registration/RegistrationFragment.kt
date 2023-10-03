package com.qemer.mwanga.dashboard.registration

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qemer.mwanga.databinding.FragmentRegistrationBinding
import com.google.android.material.textfield.TextInputLayout


class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        val rootView = binding.root

        binding.btSubmit.setOnClickListener {
            if (validateFields()) {
                val parentName = binding.parentName.editText?.text?.trim().toString()
                val parentId = binding.parentsId.editText?.text?.trim().toString()
                val parentPhoneNo = binding.parentsPhoneNumber.editText?.text?.trim().toString()
                val geolocation = binding.parentsGeoLocation.editText?.text?.trim().toString()
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
        val parentsGeoLocation =
            validateField(binding.parentsGeoLocation, "Geo Location is required")
        val numberOfChildren =
            validateField(binding.numberOfChildren, "Number of Children is required")

        return parentName.isNotEmpty() && parentsId.isNotEmpty() && parentsPhoneNumber.isNotEmpty() && parentsGeoLocation.isNotEmpty() && numberOfChildren.isNotEmpty()
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

}

