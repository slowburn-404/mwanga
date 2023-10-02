package com.qemer.mwanga.dashboard.programs

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.qemer.mwanga.dashboard.addchild.AddChildActivity
import com.qemer.mwanga.dashboard.home.RecentRegistrations
import com.qemer.mwanga.databinding.FragmentProgramsListBinding
import java.util.Locale

class ProgramsListFragment : Fragment(), OnItemClickListener {
    private var _binding: FragmentProgramsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var programsListAdapter: ProgramsListAdapter
    private var childrenInProgramList = ArrayList<RecentRegistrations>()

    inner class ProgramsListQueryTextListener : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                filterChildrenInProgramList(query)
            }
            return true
        }

        override fun onQueryTextChange(query: String?): Boolean {
            query?.let {
                filterChildrenInProgramList(query)
            }
            return true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentProgramsListBinding.inflate(inflater, container, false)

        binding.dashboardTopAppBar.setNavigationOnClickListener {
            requireActivity().finish()
        }

        val searchView = binding.programsListSearchView
        val queryTextListener = ProgramsListQueryTextListener()

        searchView.setOnQueryTextListener(queryTextListener)



        childrenInProgramList.clear()
        addSampleData()


        return binding.root
    }

    private fun addSampleData() {

        for (i in 1..3) {
            childrenInProgramList.add(RecentRegistrations("Grace Wambui", "1/1/23", "2 hours ago"))
            childrenInProgramList.add(RecentRegistrations("Bruce Wayne", "1/1/23", "2 hours ago"))
            childrenInProgramList.add(RecentRegistrations("Damian Wayne", "1/1/23", "2 hours ago"))
            childrenInProgramList.add(RecentRegistrations("Grace Wambui", "1/1/23", "2 hours ago"))
            childrenInProgramList.add(RecentRegistrations("Bruce Wayne", "1/1/23", "2 hours ago"))
            childrenInProgramList.add(RecentRegistrations("Damian Wayne", "1/1/23", "2 hours ago"))
            childrenInProgramList.add(RecentRegistrations("Angela Adisa", "1/1/23", "2 hours ago"))
            childrenInProgramList.add(RecentRegistrations("Rediet Hadera", "1/1/23", "2 hours ago"))
            childrenInProgramList.add(RecentRegistrations("Gloria Lado", "1/1/23", "2 hours ago"))
            childrenInProgramList.add(RecentRegistrations("Apiu Mary", "1/1/23", "2 hours ago"))
            childrenInProgramList.add(RecentRegistrations("Joy Mitingi", "1/1/23", "2 hours ago"))
            childrenInProgramList.add(RecentRegistrations("Gubo Diba", "1/1/23", "2 hours ago"))
        }




        programsListAdapter = ProgramsListAdapter(childrenInProgramList, this)
        binding.programsListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.programsListRecyclerView.setHasFixedSize(true)
        binding.programsListRecyclerView.adapter = programsListAdapter
    }

    private fun filterChildrenInProgramList(text: String) {
        val filteredChildrenInProgramList = ArrayList<RecentRegistrations>()
        for (child in childrenInProgramList) {
            val childInProgramsListName = child.name
            if (childInProgramsListName.lowercase().contains(text.lowercase(Locale.getDefault()))) {
                filteredChildrenInProgramList.add(child)
            }
        }

        if (filteredChildrenInProgramList.isNotEmpty()) {
            programsListAdapter.setFilteredChildrenList(filteredChildrenInProgramList)
        }
    }



    override fun onItemClick(item: RecentRegistrations) {
        requireActivity().run {
            startActivity(Intent(this, AddChildActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


