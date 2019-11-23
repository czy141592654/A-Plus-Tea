package com.example.aplustea

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class BubbleTeaViewModel(application: Application): AndroidViewModel(application){
    var bubbleTeaType = MutableLiveData<String>()
    var sweetnessRadioGroupID = MutableLiveData<Int>()
    var temperatureRadioGroupID = MutableLiveData<Int>()
    var pearlsSpinnerPosition = MutableLiveData<Int>()
    var quantityString = MutableLiveData<String>()
    var sizeRadioGroupID = MutableLiveData<Int>()
    var bubbleTeaTypePicture = MutableLiveData<Int>()
    var bubbleTeaUnitPrice = MutableLiveData<Double>()
    var orderInfo = MutableLiveData<ArrayList<String>>()
    var totalPrice = MutableLiveData<Double>()
    var cartScreenItem = MutableLiveData<ArrayList<CartScreenItem>>()
    var orderSwitchButton = MutableLiveData<Boolean>()

    var pickupordeliver =  MutableLiveData<String>()
    var cartStrings =  MutableLiveData<ArrayList<String>>()

    var name = MutableLiveData<String>()
    var phone = MutableLiveData<String>()
    var address = MutableLiveData<String>()

    var loggedIn = MutableLiveData<Boolean>()
    var firebase = MutableLiveData<DatabaseReference>()

    init {
        bubbleTeaType.value = ""
        sweetnessRadioGroupID.value = 0
        temperatureRadioGroupID.value = 0
        pearlsSpinnerPosition.value = 0
        quantityString.value = ""
        sizeRadioGroupID.value = 0
        bubbleTeaTypePicture.value = 0
        bubbleTeaUnitPrice.value = 0.0
        orderInfo.value = ArrayList()
        totalPrice.value = 0.0
        cartScreenItem.value = ArrayList()
        orderSwitchButton.value = true
        loggedIn.value = false
        firebase.value = FirebaseDatabase.getInstance().reference
    }
}