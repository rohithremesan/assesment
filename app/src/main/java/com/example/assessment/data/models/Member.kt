package com.example.assessment.data.models

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

data class Member(val name: String,
                  val percentage: MutableLiveData<String> = MutableLiveData(""),
                  val error: MutableLiveData<String> = MutableLiveData(""),
                  val amountShare: MutableLiveData<String> = MutableLiveData(""),
                  val shareCount: MutableLiveData<String> = MutableLiveData("1"))
