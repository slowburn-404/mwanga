package com.qemer.mwanga.dashboard.home

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.qemer.mwanga.R
import com.qemer.mwanga.dashboard.addchild.AddChildActivity
import com.qemer.mwanga.databinding.ItemRegistrationBinding
import com.qemer.mwanga.models.GetGuardiansResponse
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class RecentRegistrationsAdapter(private var recentRegistrationsList: ArrayList<GetGuardiansResponse>, val context: Context,
) : RecyclerView.Adapter<RecentRegistrationsAdapter.RecentRegistrationsViewHolder>() {
    private val sharedPreferences = context.getSharedPreferences("MwangaPrefs", Context.MODE_PRIVATE)
    inner class RecentRegistrationsViewHolder(private val binding: ItemRegistrationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(recentRegistrations: GetGuardiansResponse) {
            binding.name.text = recentRegistrations.parentName

            try {
                val instant = Instant.parse(recentRegistrations.createdAt)
                val localDateTime = instant.atZone(ZoneId.of("UTC")).toLocalDateTime()
//
//                val outputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
//
//                val formattedDate = localDateTime.format(outputFormat)
//                binding.date.text = formattedDate
//                binding.time.text =
                val outputDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val outputTimeFormat = DateTimeFormatter.ofPattern("HH:mm")

                val formattedDate = localDateTime.format(outputDateFormat)
                val formattedTime = localDateTime.format(outputTimeFormat)

                binding.date.text = formattedDate
                binding.time.text = formattedTime

            } catch (e: Exception) {
                e.printStackTrace()
                binding.date.text = "Invalid Date"
                binding.time.text = "Invalid Time"
            }
        }
    }

    fun filterList(filteredList: ArrayList<GetGuardiansResponse>) {
        recentRegistrationsList = filteredList
        notifyDataSetChanged()
    }

    fun setFilteredParentList(filteredList: ArrayList<GetGuardiansResponse>) {
        this.recentRegistrationsList = filteredList
        notifyDataSetChanged()
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
            val intent = Intent(context, AddChildActivity::class.java)
            intent.putExtra("fragmentToLoad", "DetailsFragment")
            sharedPreferences.edit().putString("parentId", item.id).apply()
            context.startActivity(intent)

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