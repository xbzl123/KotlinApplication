package com.example.kotlinapplication.ui.main

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
//import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.MutableStateFlow

class TestViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var contentObservable = ObservableField("123")
    val nameLivedata by lazy {
        MutableLiveData("nameLivedata")
    }

    val _nameFlow = MutableStateFlow("0")

    val stateFlow = MutableStateFlow(0)


//    val nameFlow = _nameFlow.asLiveData()

    init {

    }
}