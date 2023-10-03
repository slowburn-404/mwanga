package com.qemer.mwanga.dashboard.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.qemer.mwanga.R
import com.qemer.mwanga.databinding.ItemRegistrationBinding

class RecentRegistrationsAdapter(
    private var recentRegistrationsList: List<RecentRegistrations>,
    private val itemClickListener: HomeFragment
) : RecyclerView.Adapter<RecentRegistrationsAdapter.RecentRegistrationsViewHolder>() {

    inner class RecentRegistrationsViewHolder(private val binding: ItemRegistrationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recentRegistrations: RecentRegistrations) {
            binding.name.text = recentRegistrations.name
            binding.date.text = recentRegistrations.date
            binding.time.text = recentRegistrations.timeSpent
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RecentRegistrationsViewHolder {
        val binding =
            ItemRegistrationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentRegistrationsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecentRegistrationsViewHolder, position: Int
    ) {
        val item = recentRegistrationsList[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(item)
        }
        //set alternating background color
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context, R.color.alt_main_color
                )
            )
        } else {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context, R.color.white
                )
            )
        }
    }


    override fun getItemCount() = recentRegistrationsList.size
}