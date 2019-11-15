package com.example.aplustea

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class BubbleTeaViewModel(application: Application): AndroidViewModel(application){
    var bubbleTeaType = MutableLiveData<String>()

    init {
        bubbleTeaType.value = ""
    }
}