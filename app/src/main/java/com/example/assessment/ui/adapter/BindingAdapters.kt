package com.example.assessment.ui.adapter

import android.util.Log
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.example.assessment.data.models.Member
import com.example.assessment.ui.orderdetail.viewmodel.PaymentViewModel
import com.example.assessment.ui.util.RecyclerViewType

@BindingAdapter("onPercentageChanged", "memberObject", "viewModel", "viewType", requireAll = true)
fun EditText.bindPercentageWatcher(
    percentage: String?,
    member: Member?,
    viewModel: PaymentViewModel?,
    viewType: RecyclerViewType?
) {
    if (viewModel!=null&&member!=null&&viewType!=null){
        when(viewType){
            RecyclerViewType.AMOUNT -> {
                Log.d("binding adpter","percenteage")
                viewModel.updateMemberAmountShare(member)
            }
            RecyclerViewType.PERCENTAGE -> {
                Log.d("binding adpter","percenteage")
                viewModel.updateMemberShare(member)
            }
            RecyclerViewType.NORMAL -> {
                Log.d("binding adpter","normal")
            }
            RecyclerViewType.SHARE ->{
                Log.d("binding adpter","share")
            }
            RecyclerViewType.ITEM -> {
                Log.d("binding adpter","item")
            }
            RecyclerViewType.EVENLY -> {
                Log.d("binding adpter","evenly")
            }
        }

    }
}


@BindingAdapter("errorText")
fun setError(editText: EditText, errorMessage: String?) {
    editText.error = if (errorMessage.isNullOrBlank()) null else errorMessage
}
