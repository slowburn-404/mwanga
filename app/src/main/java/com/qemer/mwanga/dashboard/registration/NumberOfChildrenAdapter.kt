package com.qemer.mwanga.dashboard.registration

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qemer.mwanga.R
import com.qemer.mwanga.databinding.ItemAddChildrenBinding

class NumberOfChildrenAdapter(private val childrenList: List<NumberOfChildrenModel>,
                              private val onGenderSelected: (Int, String) -> Unit) :
    RecyclerView.Adapter<NumberOfChildrenAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemAddChildrenBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAddChildrenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val child = childrenList[position]

        holder.binding.childNumber.text = child.childNumber
        when (child.gender) {
            "Male" -> holder.binding.male.isChecked = true
            "Female" -> holder.binding.female.isChecked = true
            else -> {
                holder.binding.male.isChecked = false
                holder.binding.female.isChecked = false
            }
        }

        holder.binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.male -> childrenList[position].gender = "Male"
                R.id.female -> childrenList[position].gender = "Female"
                else -> childrenList[position].gender = ""
            }
        }

        holder.binding.childName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed for your case
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed for your case
            }

            override fun afterTextChanged(s: Editable?) {
                // This is called after the text in the EditText changes.

                val childName = s.toString()
                childrenList[position].childName = childName
            }
        })

        holder.binding.childDateOfBirth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val DOB = s.toString()
                childrenList[position].DOB = DOB
            }
        })

        holder.binding.childDelayedMilestones.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

                val delayedMilestones = s.toString()
                childrenList[position].delayedMilestones= delayedMilestones
            }
        })
    }

    override fun getItemCount(): Int {
        return childrenList.size
    }
}

//class NumberOfChildrenAdapter(
//    private var childrenList: List<NumberOfChildrenModel>,
//    private val selectedItems: SparseBooleanArray = SparseBooleanArray()
//) : RecyclerView.Adapter<NumberOfChildrenViewHolder>() {
//
//    inner class NumberOfChildrenViewHolder(private val binding: ItemAddChildrenBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(child: NumberOfChildrenModel) {
//            binding.childNumber.text = child.childNumber
//            binding.childName.editText?.text = child.childName
//            var childSex = binding.childSex.isChecked
//            childSex = selectedItems.get(adapterPosition)
//            childSex = child.gender
//            binding.childDateOfBirth.editText?.text = child.DOB
//            binding.childDelayedMilestones.editText?.text = child.delayedmMlestones
//
//        }
//
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup, viewType: Int
//    ): NumberOfChildrenViewHolder {
//        val binding =
//            ItemAddChildrenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return NumberOfChildrenViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(
//        holder: NumberOfChildrenViewHolder, position: Int
//    ) {
//        val item = childrenList[position]
//        holder.bind(item)
//    }
//
//    override fun getItemCount() = childrenList.size
//}