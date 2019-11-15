package com.example.aplustea

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class BubbleTeaViewModel(application: Application): AndroidViewModel(application){
    var bubbleTeaType = MutableLiveData<String>()
    var sweetnessSpinnerPosition = MutableLiveData<Int>()
    var temperatureSpinnerPosition = MutableLiveData<Int>()
    var pearlsSpinnerPosition = MutableLiveData<Int>()
    var qtyString = MutableLiveData<String>()

    init {
        bubbleTeaType.value = ""
        sweetnessSpinnerPosition.value = 0
        temperatureSpinnerPosition.value = 0
        pearlsSpinnerPosition.value = 0
        qtyString.value = ""

    }
}