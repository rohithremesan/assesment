package com.example.assessment.ui.adapter



import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.assessment.BR
import com.example.assessment.R
import com.example.assessment.data.models.Member
import com.example.assessment.databinding.SplitByItemRvBinding
import com.example.assessment.ui.orderdetail.viewmodel.PaymentViewModel
import com.example.assessment.ui.util.RecyclerViewType
import com.google.android.material.textfield.MaterialAutoCompleteTextView


class OderRecyclerViewAdapter<T: Any>(
    private val items: ArrayList<T>?=null,
    private val itemLayoutId: Int,
    private val viewType: RecyclerViewType,
    private val viewModel: PaymentViewModel?=null,
    private val lifecycleOwner: LifecycleOwner?=null,
    private val memberList: ArrayList<Member>?=null,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater, itemLayoutId, parent, false
        )
        return GenericViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
       holder as OderRecyclerViewAdapter<T>.GenericViewHolder
        when(viewType){
            RecyclerViewType.NORMAL -> holder.bind(items!!.get(position))
            RecyclerViewType.ITEM -> holder.bind(items!!.get(position))
            else -> holder.bindMember(memberList!!.get(position))
        }

    }

    inner class GenericViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            binding.setVariable(BR.data,item)
            if (viewType == RecyclerViewType.ITEM){
                Log.d("RvAdapter","b :$binding")
                binding.setVariable(BR.viewModel,viewModel)
                binding.setVariable(BR.viewType,viewType)
                val context = binding.root.context
                if (binding is SplitByItemRvBinding){
                    val dropDownValue = mutableListOf<String>()
                    memberList?.forEach {
                        dropDownValue.add(it.name)
                    }
                    Log.d("RvAdapter","dropdown :$dropDownValue")
                   val adapter= ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,dropDownValue)
                    binding.dropdown.setAdapter(adapter)
                    binding.dropdown.setOnItemClickListener { parent, view, position, id ->
                        val selected = dropDownValue[position]
                        Log.d("RvAdapter","dropdown :$selected")

                    }
                }

            }

            binding.lifecycleOwner=lifecycleOwner
            binding.executePendingBindings()
        }
        fun bindMember(item: Member) {
            binding.setVariable(BR.data,item)
            binding.setVariable(BR.viewModel,viewModel)
           if (viewType!= RecyclerViewType.SHARE){
               binding.setVariable(BR.viewType,viewType)
           }
            binding.lifecycleOwner=lifecycleOwner
            binding.executePendingBindings()
        }
    }


    override fun getItemCount(): Int {
       return when (viewType){
           RecyclerViewType.NORMAL -> items!!.size
           RecyclerViewType.ITEM -> items!!.size
          else -> memberList!!.size
       }
    }


}


