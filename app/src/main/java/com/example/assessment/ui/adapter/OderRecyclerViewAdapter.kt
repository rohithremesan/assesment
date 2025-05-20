package com.example.assessment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.assessment.BR


class OderRecyclerViewAdapter<T: Any>(
    private var items: List<T>,
    private val itemLayoutId: Int
) :
    RecyclerView.Adapter<OderRecyclerViewAdapter<T>.GenericViewHolder>() {

    inner class GenericViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            binding.setVariable(BR.data,item)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater, itemLayoutId, parent, false
        )
        return GenericViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size





    fun submitList(newList: List<T>) {
        items=newList
        notifyDataSetChanged()
    }
}


