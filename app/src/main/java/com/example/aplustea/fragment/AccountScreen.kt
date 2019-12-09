package com.example.aplustea.fragment


import android.app.Application
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.Toast
import androidx.core.view.isGone
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplustea.*
import com.example.aplustea.adapter.RecyclerViewAdapterAccountScreen
import com.example.aplustea.adapter.RecyclerViewAdapterCartScreen
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.bubble_item.*
import kotlinx.android.synthetic.main.fragment_account_screen.*
import kotlinx.android.synthetic.main.fragment_account_screen.login_button
import kotlinx.android.synthetic.main.fragment_cart_screen.*
import kotlinx.android.synthetic.main.fragment_login_screen.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class    AccountScreen : Fragment(), BubbleTeaViewModel.OnDataChangedListener {

    override fun updateList() {
        if (bubbleTeaViewModel.isOwner.value == true) {
            viewAdapter.orderInfo_array = bubbleTeaViewModel.allOrderInfo.value!!
            viewAdapter.notifyDataSetChanged()
        } else if (bubbleTeaViewModel.isOwner.value == false) {
            viewAdapter.orderInfo_array = bubbleTeaViewModel.userOrderInfo.value!!
            viewAdapter.notifyDataSetChanged()
        }
    }

    lateinit var bubbleTeaViewModel: BubbleTeaViewModel
    lateinit var viewAdapter: RecyclerViewAdapterAccountScreen
    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var mediaPlayer: MediaPlayer


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bubbleTeaViewModel = activity?.run {
            ViewModelProviders.of(this).get(BubbleTeaViewModel::class.java)
        } ?: throw Exception("activity invalid")
        bubbleTeaViewModel.listener = this
        // Inflate the layout for this fragment
        //////////////////////////////////////////////////////////////////////////
        mediaPlayer = MediaPlayer.create(context, R.raw.spinning)
        ///////////////////////////////////////////////////////////////////////////
        return inflater.inflate(R.layout.fragment_account_screen, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // if the user is already logged in, then read their info
        if(bubbleTeaViewModel.loggedIn.value == true){
            if(bubbleTeaViewModel.isOwner.value == false) {
                Toast.makeText(context, "Import Your Info To Register", Toast.LENGTH_LONG).show()
            }
        }

        if(bubbleTeaViewModel.loggedIn.value == true){
            login_button.setText("Update Info")
            loggedAlready_buttonA.isGone = true
            name_editTextA.setText(bubbleTeaViewModel.name.value)
            address_editTextA.setText(bubbleTeaViewModel.address.value)
            phone_texteditA.setText(bubbleTeaViewModel.phone.value)
        }else{
            login_button.setText("Register")
            loggedAlready_buttonA.isGone = false
        }

        loggedAlready_buttonA.setOnClickListener {
            if (bubbleTeaViewModel.loggedAlreadyButtonClicked.value == false) {
                name_editTextA.visibility = View.INVISIBLE
                address_editTextA.visibility = View.INVISIBLE
                loggedAlready_buttonA.text = "If you did not login before (Go Back)"
                login_button.text = "Log In"
                Toast.makeText(context, "Import Your Phone To Log In", Toast.LENGTH_LONG).show()
                bubbleTeaViewModel.loggedAlreadyButtonClicked.value = true
            } else if (bubbleTeaViewModel.loggedAlreadyButtonClicked.value == true) {
                name_editTextA.visibility = View.VISIBLE
                address_editTextA.visibility = View.VISIBLE
                loggedAlready_buttonA.text = "Login"
                login_button.text = "Register"
                bubbleTeaViewModel.loggedAlreadyButtonClicked.value = false
            }
        }

        login_button.setOnClickListener {
            updateList()
            if (bubbleTeaViewModel.loggedIn.value == true) {
                if (phone_texteditA.text.toString().isNotEmpty() && name_editTextA.text.toString().isNotEmpty() && address_editTextA.text.toString().isNotEmpty()) {
                    bubbleTeaViewModel.deleteInfo(
                        PersonalInfo(
                            bubbleTeaViewModel.phone.value!!,
                            bubbleTeaViewModel.name.value!!,
                            bubbleTeaViewModel.address.value!!
                        )
                    )
                    bubbleTeaViewModel.insertInfo(
                        PersonalInfo(
                            phone_texteditA.text.toString(),
                            name_editTextA.text.toString(),
                            address_editTextA.text.toString()
                        )
                    )
                    Toast.makeText(context, "Updated", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Information Missing", Toast.LENGTH_LONG).show()
                }
            }
            else if(bubbleTeaViewModel.loggedIn.value == false && bubbleTeaViewModel.loggedAlreadyButtonClicked.value == false){
                if (phone_texteditA.text.toString().isNotEmpty() && name_editTextA.text.toString().isNotEmpty() && address_editTextA.text.toString().isNotEmpty()){
                    bubbleTeaViewModel.name.value = name_editTextA.text.toString()
                    bubbleTeaViewModel.phone.value = phone_texteditA.text.toString()
                    bubbleTeaViewModel.address.value = address_editTextA.text.toString()
                    bubbleTeaViewModel.insertInfo(
                        PersonalInfo(
                            phone_texteditA.text.toString(),
                            name_editTextA.text.toString(),
                            address_editTextA.text.toString()
                        )
                    )
                    bubbleTeaViewModel.loggedIn.value = true
                    if(bubbleTeaViewModel.placeOrderButtonClicked.value == true){
                        findNavController().navigate(R.id.action_accountScreen_to_cancelOrder)
                        bubbleTeaViewModel.placeOrderButtonClicked.value = false
                    }else{
                        loggedAlready_buttonA.isGone = true
                        login_button.setText("Update Info")
                    }
                    Toast.makeText(context, "You can update your personal information by changing the info and press Update  ", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context, "Information Missing", Toast.LENGTH_LONG).show()
                }
            }
            else if(bubbleTeaViewModel.loggedIn.value == false && bubbleTeaViewModel.loggedAlreadyButtonClicked.value == true){
                if(phone_texteditA.text.isNotBlank()){
                    bubbleTeaViewModel.phone.value = phone_texteditA.text.toString()

                    // get info from local database
                    var personalInfo =
                        bubbleTeaViewModel.getInfoByPhone(phone_texteditA.text.toString())
                    if (personalInfo != null) {
                        bubbleTeaViewModel.loggedIn.value = true
                        bubbleTeaViewModel.name.value = personalInfo.name
                        bubbleTeaViewModel.address.value = personalInfo.address
                        login_button.setText("Update Info")
                        name_editTextA.visibility = View.VISIBLE
                        address_editTextA.visibility = View.VISIBLE
                        name_editTextA.setText(personalInfo.name)
                        address_editTextA.setText(personalInfo.address)
                        loggedAlready_buttonA.visibility = View.GONE
                        bubbleTeaViewModel.firebase.value?.child("Users by Phone")?.child("USELESS")?.setValue(
                            System.currentTimeMillis())
                        Toast.makeText(context, "You can update your personal information by changing the info and press Update  ", Toast.LENGTH_LONG).show()
                    }
                    else {
                        Toast.makeText(context, "You Have Not Logged In Yet", Toast.LENGTH_LONG)
                            .show()
                    }
                    if(bubbleTeaViewModel.placeOrderButtonClicked.value == true){
                        findNavController().navigate(R.id.action_accountScreen_to_cancelOrder)
                        bubbleTeaViewModel.placeOrderButtonClicked.value = false
                    }
                }
            }

        }
        clearOrders.setOnClickListener {
            if (bubbleTeaViewModel.isOwner.value == true) {
                findNavController().navigate(R.id.action_accountScreen_to_ownerScreen)

            }
        }

        viewAdapter = RecyclerViewAdapterAccountScreen(ArrayList())
        viewManager = LinearLayoutManager(context)

        account_screen_recyclerview.apply {
            this.adapter = viewAdapter
            this.layoutManager = viewManager
        }
    }

    override fun onResume() {
        super.onResume()

        if(bubbleTeaViewModel.loggedIn.value == true){
            login_button.setText("Update Info")
            loggedAlready_buttonA.isGone = true
            println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + bubbleTeaViewModel.name.value)
            name_editTextA.setText(bubbleTeaViewModel.name.value)
            address_editTextA.setText(bubbleTeaViewModel.address.value)
            phone_texteditA.setText(bubbleTeaViewModel.phone.value)
        }else{
            login_button.setText("Register")
            loggedAlready_buttonA.isGone = false
        }

        updateList()

        if (bubbleTeaViewModel.isOwner.value == true) {
            viewAdapter.orderInfo_array = bubbleTeaViewModel.allOrderInfo.value!!
            viewAdapter.notifyDataSetChanged()
            login_button.visibility = View.GONE
            loggedAlready_buttonA.visibility = View.GONE
            name_editTextA.visibility = View.GONE
            address_editTextA.visibility = View.GONE
            phone_texteditA.visibility = View.GONE
            clearOrders.visibility = View.VISIBLE
            your_orders_text.setText("All The Orders")

            bubbleTeaViewModel.firebase2.value?.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if(bubbleTeaViewModel.isOwner.value == true) {
                        mediaPlayer.start()
                    }
                }
            })

        } else if (bubbleTeaViewModel.isOwner.value == false) {
            viewAdapter.orderInfo_array = bubbleTeaViewModel.userOrderInfo.value!!
            viewAdapter.notifyDataSetChanged()
            login_button.visibility = View.VISIBLE
            name_editTextA.visibility = View.VISIBLE
            address_editTextA.visibility = View.VISIBLE
            phone_texteditA.visibility = View.VISIBLE
            clearOrders.visibility = View.GONE
            if(bubbleTeaViewModel.loggedAlreadyButtonClicked.value == true){
                login_button.text = "LOG IN"
                name_editTextA.visibility = View.INVISIBLE
                address_editTextA.visibility = View.INVISIBLE
            }
        }
    }

}
