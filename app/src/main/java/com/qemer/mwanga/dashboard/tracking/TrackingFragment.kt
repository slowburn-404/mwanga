package com.qemer.mwanga.dashboard.tracking

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.qemer.mwanga.databinding.FragmentTrackingBinding
import com.qemer.mwanga.models.GetGuardiansResponse
import java.util.Locale

class TrackingFragment : Fragment(), ChildrenItemOnClickListener {
    private var _binding: FragmentTrackingBinding? = null
    private val binding get() = _binding!!

    private lateinit var childrenAdapter: ChildrenAdapter
    private var childrenList = ArrayList<Child>()

    inner class ChildrenListQueryTextListener : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                filterChildrenList(query)
            }
            return true
        }

        override fun onQueryTextChange(query: String?): Boolean {
            query?.let {
                filterChildrenList(query)
            }
            return true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrackingBinding.inflate(inflater, container, false)

        binding.childrenTopAppBar.setNavigationOnClickListener {
            requireActivity().finish()
        }
        val searchView = binding.childrenSearchView
        val queryTextListener = ChildrenListQueryTextListener()
        searchView.setOnQueryTextListener(queryTextListener)

        childrenList.clear()
        addChildren()


        return binding.root
    }

    private fun addChildren() {
        for (i in 1..2) {
            childrenList.add(Child("Grace Wambui", "1/1/23", "2 hours"))
            childrenList.add(Child("Riggy G", "1/1/23", "2 hours"))
            childrenList.add(Child("Mtoza Ushuru", "1/1/23", "2 hours"))
            childrenAdapter = ChildrenAdapter(childrenList, this)
            binding.childrenRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.childrenRecyclerView.setHasFixedSize(true)
            binding.childrenRecyclerView.adapter = childrenAdapter
        }
    }

    private fun filterChildrenList(query: String) {
        val filteredList = childrenList.filter {
            it.name.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))
        } as ArrayList<Child>

        childrenAdapter.setFilteredChildrenList(filteredList)
    }

    override fun onChildItemClick(item: Child) {
        requireActivity().run {
            startActivity(Intent(this, ChildDetailsActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}