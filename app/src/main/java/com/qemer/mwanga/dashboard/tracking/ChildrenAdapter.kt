package com.qemer.mwanga.dashboard.tracking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.qemer.mwanga.R
import com.qemer.mwanga.databinding.ItemChildBinding
import com.qemer.mwanga.models.GetChildrenResponse

class ChildrenAdapter(
    private var childrenList: List<Child>, private val itemClickListener: TrackingFragment
) : RecyclerView.Adapter<ChildrenAdapter.ChildrenViewHolder>(), ChildrenItemOnClickListener {

    inner class ChildrenViewHolder(private val binding: ItemChildBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(children: Child) {
            binding.name.text = children.name
            binding.date.text = children.date
            binding.time.text = children.timeSpent
        }
    }

    fun setFilteredChildrenList(filteredList: ArrayList<Child>) {
        this.childrenList = filteredList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ChildrenViewHolder {
        val binding = ItemChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChildrenViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ChildrenViewHolder, position: Int
    ) {
        val item = childrenList[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            itemClickListener.onChildItemClick(item)
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

    override fun onChildItemClick(item: Child){

    }


    override fun getItemCount() = childrenList.size
}