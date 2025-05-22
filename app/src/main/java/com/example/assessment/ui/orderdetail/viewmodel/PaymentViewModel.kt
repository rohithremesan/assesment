package com.example.assessment.ui.orderdetail.viewmodel

import androidx.lifecycle.*
import com.example.assessment.data.models.Member
import dagger.hilt.android.lifecycle.HiltViewModel
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor() : ViewModel() {

    private val _members = MutableLiveData<List<Member>>()
    val members: LiveData<List<Member>> = _members
    private var amount: Double=0.0

    fun setValueForCalculation(members: List<Member>, amount: Double) {
        _members.value = members
        this.amount=amount
    }

    fun updateMemberShare(member: Member) {

        val percentageInput = member.percentage.value
        val percentage = percentageInput?.toBigDecimalOrNull() ?: BigDecimal.ZERO
        val totalBillBD = BigDecimal.valueOf(amount)

        member.error.value = null

        if (percentage > BigDecimal(100)) {
            member.error.value = "Enter a valid percentage"
            return
        }

        val totalPercentage = members.value.sumOf {
            if (it == member) percentage else (it.percentage.value?.toBigDecimalOrNull() ?: BigDecimal.ZERO)
        }

        if (totalPercentage > BigDecimal(100)) {
            member.error.value = "Total percentage cannot exceed 100%"
            return
        }

        val calculatedShare = totalBillBD.multiply(percentage).divide(BigDecimal(100), 2, RoundingMode.HALF_UP)
        member.amountShare.value = calculatedShare.toString()
    }

    fun updateMemberAmountShare(member: Member) {
        val inputAmountStr = member.amountShare.value
        val inputAmount = inputAmountStr?.toBigDecimalOrNull() ?: BigDecimal.ZERO
        val totalBillBD = BigDecimal.valueOf(amount)

        member.error.value = null

        if (inputAmount < BigDecimal.ZERO) {
            member.error.value = "Enter a valid amount"
            return
        }

        val totalAssignedAmount = members.value.sumOf {
            if (it == member) inputAmount else (it.amountShare.value?.toBigDecimalOrNull() ?: BigDecimal.ZERO)
        }

        if (totalAssignedAmount > totalBillBD) {
            member.error.value = "Total assigned amount exceeds total bill"
            return
        }


    }

    fun iniSplitByShareInitialValue(){
        updateAllMembersSplitByShares()
    }


    fun updateAllMembersSplitByShares() {
        val totalBillBD = BigDecimal.valueOf(amount)

        // Calculate total shares
        val totalShares = members.value.sumOf {
            it.shareCount.value?.toIntOrNull() ?: 0
        }.takeIf { it > 0 } ?: 1 // Avoid division by zero

        // Recalculate each member's share
        members.value.forEach { member ->
            val memberShares = member.shareCount.value?.toIntOrNull() ?: 0
            val shareFraction = BigDecimal(memberShares)
                .divide(BigDecimal(totalShares), 6, RoundingMode.HALF_UP)
            val shareAmount = totalBillBD
                .multiply(shareFraction)
                .setScale(2, RoundingMode.HALF_UP)

            member.amountShare.value = shareAmount.toString()
        }
    }



    fun increaseShareCount(member: Member) {
        val current = member.shareCount.value?.toIntOrNull() ?: 0
        member.shareCount.value = (current + 1).toString()
        updateAllMembersSplitByShares()
    }

    fun decreaseShareCount(member: Member) {
        val current = member.shareCount.value?.toIntOrNull() ?: 0
        if (current>=2){
            member.shareCount.value = (current - 1).toString()
            updateAllMembersSplitByShares()
        }


    }


    fun calculateSplitEvently(){
        val totalmembers = members.value.size
        val eachShareAmount = amount/totalmembers
        _members.value.forEach {
            it.amountShare.value=String.format("%.2f", eachShareAmount)
        }
    }




}

