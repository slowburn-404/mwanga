package com.qemer.mwanga.dashboard.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.qemer.mwanga.dashboard.SuccessModalFragment
import com.qemer.mwanga.databinding.FragmentAllParentsChildrenBinding

class AllParentsChildrenFragment : Fragment() {

    private var _binding: FragmentAllParentsChildrenBinding? = null
    private val binding get() = _binding!!

    private var parentsChildrenList = ArrayList<ParentsChildrenModel>()
    private lateinit var parentsChildrenAdapter: ParentsChildrenAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAllParentsChildrenBinding.inflate(inflater, container, false)
        binding.allParentsChildrenTopAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btSubmit.setOnClickListener {
            showSuccessModal()
        }
        parentsChildrenList.clear()
//        addSampleData()

        return binding.root
    }

    private fun showSuccessModal() {
        val successModal = SuccessModalFragment()
        successModal.show(requireActivity().supportFragmentManager, SuccessModalFragment.TAG)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}