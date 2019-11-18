package com.example.aplustea

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class BubbleTeaViewModel(application: Application): AndroidViewModel(application){
    var bubbleTeaType = MutableLiveData<String>()
    var sweetnessRadioGroupID = MutableLiveData<Int>()
    var temperatureRadioGroupID = MutableLiveData<Int>()
    var pearlsSpinnerPosition = MutableLiveData<Int>()
    var quantityString = MutableLiveData<String>()
    var sizeRadioGroupID = MutableLiveData<Int>()
    var bubbleTeaTypePicture = MutableLiveData<Int>()
    var bubbleTeaPrice = MutableLiveData<Double>()

    init {
        bubbleTeaType.value = ""
        sweetnessRadioGroupID.value = 0
        temperatureRadioGroupID.value = 0
        pearlsSpinnerPosition.value = 0
        quantityString.value = ""
        sizeRadioGroupID.value = 0
        bubbleTeaTypePicture.value = 0
        bubbleTeaPrice.value = 0.0

    }
}