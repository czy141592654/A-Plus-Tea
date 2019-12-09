package com.example.aplustea

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_login_screen.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BubbleTeaViewModel(application: Application) : AndroidViewModel(application) {
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

    var pickupordeliver = MutableLiveData<String>()
    var cartStrings = MutableLiveData<ArrayList<String>>()

    var name = MutableLiveData<String>()
    var phone = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    var currentTime = MutableLiveData<String>()

    var loggedIn = MutableLiveData<Boolean>()
    var firebase = MutableLiveData<DatabaseReference>()
    var firebase2 = MutableLiveData<DatabaseReference>()

    val database = BubbleDB.getDBObject(getApplication<Application>().applicationContext)


    var allOrderInfo = MutableLiveData<ArrayList<UserOrOwnerInfo>>()
    var userOrderInfo = MutableLiveData<ArrayList<UserOrOwnerInfo>>()
    var getTimeFromFirebase = MutableLiveData<String>()
    var getPhoneNumberFromFirebase = MutableLiveData<String>()
    var getAddressFromFirebase = MutableLiveData<String>()
    var getNameFromFirebase = MutableLiveData<String>()
    var test = ""
    var isOwner = MutableLiveData<Boolean>()

    var loggedAlreadyButtonClicked = MutableLiveData<Boolean>()
    var placeOrderButtonClicked = MutableLiveData<Boolean>()



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
        isOwner.value = false
        firebase.value = FirebaseDatabase.getInstance().reference
        currentTime.value = ""
        cartStrings.value = ArrayList()
        allOrderInfo.value = ArrayList()
        userOrderInfo.value = ArrayList()
        getTimeFromFirebase.value = ""
        getPhoneNumberFromFirebase.value = ""
        getAddressFromFirebase.value = ""
        getNameFromFirebase.value = ""
        loggedAlreadyButtonClicked.value = false
        placeOrderButtonClicked.value = false
        firebase2.value = FirebaseDatabase.getInstance().reference



        firebase.value?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                var store = ""
                allOrderInfo.value!!.clear()
                userOrderInfo.value!!.clear()
                p0.child("Users by Phone").children.forEach {
                    it.key?.let {
                        getPhoneNumberFromFirebase.value = it
                    }
                    it.children.forEach {
                        //FOR EACH TIME
                        it.key?.let {
                            getTimeFromFirebase.value = it
                        }
                        it.children.forEach {
                            test = it.key.toString()
                            it.key?.let {
                                store = it
                            }

                            if (store == "Address") {
                                it.value?.let {
                                    getAddressFromFirebase.value = it.toString()
                                }

                            } else if (store == "Name") {
                                it.value?.let {
                                    getNameFromFirebase.value = it.toString()
                                }

                            }
                        }
                        it.child("Order Info")
                            .children.forEach {
                            allOrderInfo.value?.add(UserOrOwnerInfo(it.value.toString() + "\n" + "NAME: " + getNameFromFirebase.value + "\n" + "ADDRESS: " + getAddressFromFirebase.value + "\n" + "PHONE: " + getPhoneNumberFromFirebase.value + "\n" + "TIME: " + getTimeFromFirebase.value))
                        }

                        // get current user order info
                        if (getPhoneNumberFromFirebase.value == phone.value) {
                            it.child("Order Info").children.forEach {
                                // value is empty
                                it.value.let {
                                    userOrderInfo.value?.add(UserOrOwnerInfo(it.toString()))
                                }
                            }
                        }

                    }

                }
                listener?.updateList()

            }
        })
    }

    var listener: OnDataChangedListener? = null

    interface OnDataChangedListener {
        fun updateList()
    }


    // create async function for uploding data
    suspend fun upload(name: String, address: String, time: String, phone: String) =
        withContext(Dispatchers.IO) {

            var counter = 1
            firebase.value?.child("Users by Phone")?.child(phone)?.child(time)?.child("Name")
                ?.setValue(name)
            firebase.value?.child("Users by Phone")?.child(phone)?.child(time)?.child("Address")
                ?.setValue(address)

            for (order in cartStrings.value!!) {

                firebase.value?.child("Users by Phone")
                    ?.child(phone)?.child(time)?.child("Order Info")?.child("$counter")
                    ?.setValue(order)
                counter++
            }

            cartStrings.value!!.clear()


        }

    fun uploadData() {
        viewModelScope.launch {
            async { upload(name.value!!, address.value!!, currentTime.value!!, phone.value!!) }

        }
    }

    fun insertInfo(info: PersonalInfo) {
        database?.bubbleDAO()?.insert(info)
    }

    fun getInfoByPhone(phone: String): PersonalInfo {
        return database?.bubbleDAO()!!.getInfoByPhone(phone)
    }

    fun getAllInfo(): List<PersonalInfo> {
        return database?.bubbleDAO()!!.getAll()
    }

    fun deleteInfo(info: PersonalInfo) {
        database?.bubbleDAO()?.delete(info)
    }
}