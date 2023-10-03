package com.qemer.mwanga.dashboard.programs

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.qemer.mwanga.R
import com.qemer.mwanga.dashboard.home.RecentRegistrations
import com.qemer.mwanga.databinding.ItemRegistrationBinding
import com.qemer.mwanga.models.GetChildrenResponse

class ProgramsListAdapter(
    private var programsList: ArrayList<GetChildrenResponse>, val context: Context
) : RecyclerView.Adapter<ProgramsListAdapter.ProgramsListViewHolder>() {

    inner class ProgramsListViewHolder(private val binding: ItemRegistrationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recentRegistrations: GetChildrenResponse) {
            binding.name.text = recentRegistrations.childName
            binding.date.text = recentRegistrations.createdAt
//            binding.time.text = recentRegistrations.timeSpent
        }
    }
    fun setFilteredChildrenList(filteredList: ArrayList<RecentRegistrations>) {
//        this.programsList = filteredList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ProgramsListViewHolder {
        val binding =
            ItemRegistrationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProgramsListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProgramsListViewHolder, position: Int
    ) {
        val item = programsList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
//            itemClickListener.onItemClick(item)
        }

        //set alternating background color
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context, R.color.alt_main_color
                )
            )
        }else{
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context, R.color.white
                )
            )
        }


    }

    override fun getItemCount() = programsList.size
}