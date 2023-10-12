package com.qemer.mwanga.dashboard.registration

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qemer.mwanga.databinding.ItemParentsChildrenBinding

class ParentsChildrenAdapter(private var parentsChildrenList: ArrayList<NumberOfChildrenModel>) : RecyclerView.Adapter<ParentsChildrenAdapter.ParentsChildrenViewHolder>() {

    inner class ParentsChildrenViewHolder(private val binding: ItemParentsChildrenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(parentsChildren: NumberOfChildrenModel) {
            binding.childName.text = parentsChildren.childName
            binding.childNumber.text = "Child: ${parentsChildren.childNumber}"
            binding.childDateOfBirth.text = parentsChildren.DOB
            binding.childGender.text = parentsChildren.gender
            binding.childDelayedMilestone.text = parentsChildren.delayedMilestones
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentsChildrenViewHolder {
        val binding = ItemParentsChildrenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParentsChildrenViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentsChildrenAdapter.ParentsChildrenViewHolder, position: Int) {
        val item = parentsChildrenList[position]
        holder.bind(item)
    }

    override fun getItemCount() = parentsChildrenList.size
}
