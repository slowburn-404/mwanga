package com.qemer.mwanga.dashboard.registration

import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.qemer.mwanga.R
import com.qemer.mwanga.dashboard.home.RecentRegistrations
import com.qemer.mwanga.dashboard.programs.ProgramsListAdapter
import com.qemer.mwanga.databinding.FragmentNumberOfChildrenBinding

class NumberOfChildrenFragment : Fragment() {
    private var _binding: FragmentNumberOfChildrenBinding? = null
    private val binding get() = _binding!!

    private var childrenList = ArrayList<NumberOfChildrenModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNumberOfChildrenBinding.inflate(inflater, container, false)
        childrenList.clear()
        addForms()


        binding.btNext.setOnClickListener {
            findNavController().navigate(R.id.action_numberOfChildrenFragment_to_parentsChildrenFragment)
        }
        binding.numberOfChildrenTopAppBar.setNavigationOnClickListener {
            requireActivity().finish()
        }
        binding.btBack.setOnClickListener {
            requireActivity().finish()
        }




        return binding.root
    }

    private fun getNumberOfChildren(): String? {
        requireActivity().run {
            return intent.getStringExtra("children")

        }
    }

    private fun addForms() {
        val numberOfChildren = getNumberOfChildren()
        for (i in 1..numberOfChildren!!.toInt()) {
            childrenList.add(
                NumberOfChildrenModel(
                    "Child $i",
                    SpannableStringBuilder("Child's Name"),
                    SpannableStringBuilder("DOB"),
                    true,
                    SpannableStringBuilder("Delayed Milestones")
                )
            )


            val numberOfChildrenAdapter = NumberOfChildrenAdapter(childrenList)
            binding.numberOfChildrenRecyclerView.layoutManager =
                LinearLayoutManager(requireContext())
            binding.numberOfChildrenRecyclerView.setHasFixedSize(true)
            binding.numberOfChildrenRecyclerView.adapter = numberOfChildrenAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}