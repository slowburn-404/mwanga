package com.qemer.mwanga.dashboard.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.qemer.mwanga.R
import com.qemer.mwanga.databinding.ItemRegistrationBinding
import com.qemer.mwanga.models.GetGuardiansResponse
import java.text.SimpleDateFormat
import java.util.TimeZone

class RecentRegistrationsAdapter(
    private var recentRegistrationsList: ArrayList<GetGuardiansResponse>, val context: Context
) : RecyclerView.Adapter<RecentRegistrationsAdapter.RecentRegistrationsViewHolder>() {
    inner class RecentRegistrationsViewHolder(private val binding: ItemRegistrationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recentRegistrations: GetGuardiansResponse) {
            binding.name.text = recentRegistrations.parentName
            binding.date.text = recentRegistrations.createdAt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentRegistrationsViewHolder {
        val binding = ItemRegistrationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentRegistrationsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecentRegistrationsViewHolder, position: Int
    ) {
        val item = recentRegistrationsList[position]
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