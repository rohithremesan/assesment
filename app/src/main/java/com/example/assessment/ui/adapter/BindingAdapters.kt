package com.example.assessment.ui.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.example.assessment.R
import com.example.assessment.data.models.Member
import com.example.assessment.ui.orderdetail.viewmodel.PaymentViewModel

@BindingAdapter("onPercentageChanged", "memberObject", "viewModel", requireAll = true)
fun EditText.bindPercentageWatcher(
    percentage: String?,
    member: Member?,
    viewModel: PaymentViewModel?
) {
    println(percentage)
    if (viewModel!=null&&member!=null){
        viewModel.updateMemberShare(member)
    }
}
