package com.ckb.gravitygame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    val myLiveModel = MutableLiveData<MyModel>()

    init {
        myLiveModel.value = MyModel("Default", 0)
    }
}