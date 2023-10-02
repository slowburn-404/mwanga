package com.qemer.mwanga.dashboard.registration

import android.util.SparseBooleanArray
import androidx.core.view.children
import com.qemer.mwanga.dashboard.programs.OnItemClickListener
import com.qemer.mwanga.databinding.ItemAddChildrenBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.qemer.mwanga.R
import com.qemer.mwanga.dashboard.registration.NumberOfChildrenAdapter.NumberOfChildrenViewHolder

class NumberOfChildrenAdapter(
    private var childrenList: List<NumberOfChildrenModel>,
    private val selectedItems: SparseBooleanArray = SparseBooleanArray()
) : RecyclerView.Adapter<NumberOfChildrenViewHolder>() {

    inner class NumberOfChildrenViewHolder(private val binding: ItemAddChildrenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(child: NumberOfChildrenModel) {
            binding.childNumber.text = child.childNumber
            binding.childName.editText?.text = child.childName
            var childSex = binding.childSex.isChecked
            childSex = selectedItems.get(adapterPosition)
            childSex = child.gender
            binding.childDateOfBirth.editText?.text = child.DOB
            binding.childDelayedMilestones.editText?.text = child.delayedmMlestones

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): NumberOfChildrenViewHolder {
        val binding =
            ItemAddChildrenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NumberOfChildrenViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NumberOfChildrenViewHolder, position: Int
    ) {
        val item = childrenList[position]
        holder.bind(item)
    }

    override fun getItemCount() = childrenList.size
}