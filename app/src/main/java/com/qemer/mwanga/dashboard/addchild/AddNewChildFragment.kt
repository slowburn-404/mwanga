package com.qemer.mwanga.dashboard.addchild

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.qemer.mwanga.R
import com.qemer.mwanga.dashboard.SuccessModalFragment
import com.qemer.mwanga.databinding.FragmentAddNewChildBinding
import com.google.android.material.textfield.TextInputLayout
import com.qemer.mwanga.api.ApiLoginClient
import com.qemer.mwanga.dashboard.MainDashboardActivity
import com.qemer.mwanga.models.ChildCreateRequest
import com.qemer.mwanga.models.ChildCreateResponse
import com.qemer.mwanga.models.LoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class AddNewChildFragment : Fragment() {
    private var _binding: FragmentAddNewChildBinding? = null
    private val binding get() = _binding!!
    private lateinit var apiClient: ApiLoginClient
    var selectedGender: String? = null
    var selectedItem:String?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNewChildBinding.inflate(inflater, container, false)
        apiClient = ApiLoginClient()
        binding.addNewChildTopAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.ltBirth.setOnClickListener {
            showDatePicker()
        }

        binding.milestonesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                selectedItem = parentView.getItemAtPosition(position).toString()
                Log.d("SelectedMilestone", selectedItem!!)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }

        binding.btSubmit.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("MwangaPrefs", Context.MODE_PRIVATE)
            val parentId = sharedPreferences.getString("parentId", null)

            if (TextUtils.isEmpty(binding.childName.text.toString().trim())) {
                binding.childName.error = "Child's name is required"
            } else if(binding.childDateOfBirth.text.toString() == "Date of birth"){
                Snackbar.make(it, "Select a birth date", Snackbar.LENGTH_SHORT).show()
            } else if (binding.radioGroup.checkedRadioButtonId == -1) {
                Snackbar.make(it, "gender not picked", Snackbar.LENGTH_SHORT).show()
            } else {
                if (binding.male.isChecked) {
                    selectedGender = "Male"
                } else if (binding.female.isChecked) {
                    selectedGender = "Female"
                }

                val progressDialog = ProgressDialog(requireContext())
                progressDialog.setCancelable(false) // set cancelable to false
                progressDialog.setMessage("Submitting...") // set message
                progressDialog.show()

                val requestData = ChildCreateRequest(
                    binding.childName.text.toString().trim(),
                    binding.childDateOfBirth.text.toString(),
                    selectedGender!!,
                    selectedItem!!,
                    parentId.toString()
                )
                Log.d("requestData", requestData.toString())
                apiClient.getApiService(requireContext()).childRegistration(requestData).enqueue(object : Callback<ChildCreateResponse> {
                    override fun onResponse(call: Call<ChildCreateResponse>, response: Response<ChildCreateResponse>) {
                        if (response.isSuccessful) {
                            progressDialog.dismiss()
                            Snackbar.make(it, "Success", Snackbar.LENGTH_SHORT).show()
                            val intent = Intent(requireContext(), MainDashboardActivity::class.java)
                            startActivity(intent)
                        } else {
                            progressDialog.dismiss()
                            Snackbar.make(it, "Failed to submit", Snackbar.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ChildCreateResponse>, t: Throwable) {
                        Log.e("Gideon", "onFailure: ${t.message}")
                        Snackbar.make(it, "${t.message}", Snackbar.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                    }
                })
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
//    private fun validateFields(): Boolean {
//        val childName = validateField(binding.childName, "Child's name is required")
//        val childAge = validateField(binding.childDateOfBirth, "Date of birth is required")

        // Return true if both fields are valid, false otherwise
//        return childName.isNotEmpty() && childAge.isNotEmpty()
//    }

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

private fun showDatePicker() {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        requireContext(),
        { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
            binding.childDateOfBirth.text = selectedDate
        },
        year, month, day
    )

    datePickerDialog.show()
}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
