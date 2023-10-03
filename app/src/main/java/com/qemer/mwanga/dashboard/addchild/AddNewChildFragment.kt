package com.qemer.mwanga.dashboard.addchild

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.qemer.mwanga.R
import com.qemer.mwanga.dashboard.SuccessModalFragment
import com.qemer.mwanga.databinding.FragmentAddNewChildBinding
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Locale



class AddNewChildFragment : Fragment() {
    private var _binding: FragmentAddNewChildBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNewChildBinding.inflate(inflater, container, false)

        binding.addNewChildTopAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()

        }

//        binding.childDateOfBirth.setEndIconOnClickListener {
//            showDatePickerDialog()
//        }
        binding.cbMale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.cbFemale.isChecked = false
        }
        binding.cbFemale.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) binding.cbMale.isChecked = false
        }


        binding.btSubmit.setOnClickListener {
            if (validateFields()) {
                showSuccessModal()
            } else {
                Toast.makeText(
                    requireContext(), "Please fill in all required fields", Toast.LENGTH_SHORT
                ).show()
            }
        }

        val spinner = binding.milestonesSpinner
        val adapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.milestones_array, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        return binding.root
    }
    private fun validateFields(): Boolean {
        val childName = validateField(binding.childName, "Child's name is required")
        val childAge = validateField(binding.childDateOfBirth, "Date of birth is required")

        // Return true if both fields are valid, false otherwise
        return childName.isNotEmpty() && childAge.isNotEmpty()
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

    private fun showSuccessModal() {
        val successModal = SuccessModalFragment()
        successModal.show(requireActivity().supportFragmentManager, SuccessModalFragment.TAG)
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                binding.childDateOfBirth.editText?.setText(formattedDate)
            }, year, month, day
        )

        datePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
