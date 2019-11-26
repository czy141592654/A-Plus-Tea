package com.example.aplustea.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.aplustea.BubbleTeaViewModel
import com.example.aplustea.PersonalInfo

import com.example.aplustea.R

import kotlinx.android.synthetic.main.fragment_login_screen.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class LoginScreen : Fragment() {
    lateinit var bubbleTeaViewModel: BubbleTeaViewModel
    var count = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bubbleTeaViewModel = activity?.run {
            ViewModelProviders.of(this).get(BubbleTeaViewModel::class.java)
        } ?: throw Exception("activity invalid")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loggedAlready_button.setOnClickListener {
            if (bubbleTeaViewModel.loggedIn.value == false) {
                name_editTextL.visibility = View.INVISIBLE
                address_editTextL.visibility = View.INVISIBLE
                loggedAlready_button.text = "If you did not login before (Go Back)"
                bubbleTeaViewModel.loggedIn.value = true
            } else if (bubbleTeaViewModel.loggedIn.value == true) {
                name_editTextL.visibility = View.VISIBLE
                address_editTextL.visibility = View.VISIBLE
                loggedAlready_button.text = "LOGGED IN BEORE?"
                bubbleTeaViewModel.loggedIn.value = false
            }
        }
        login_button.setOnClickListener {
            // USER HAS NOT LOGGED IN YET SEND NAME, PHONE, ADDRESS, ORDER INFO TO FIREBASE, CRASH ON SENDING ORDER
            if ((bubbleTeaViewModel.loggedIn.value == false) && (name_editTextL.text.isNotBlank() && phone_texteditL.text.isNotBlank() && address_editTextL.text.toString().isNotBlank())) {

                val currentDate = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(Date())
                val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                var time = currentDate + " " + currentTime

                bubbleTeaViewModel.name.value = name_editTextL.text.toString()
                bubbleTeaViewModel.phone.value = phone_texteditL.text.toString()
                bubbleTeaViewModel.address.value = address_editTextL.text.toString()
                bubbleTeaViewModel.currentTime.value = time

                bubbleTeaViewModel.insertInfo(PersonalInfo(phone_texteditL.text.toString(),name_editTextL.text.toString(),address_editTextL.text.toString()))
                //doing async process here
                bubbleTeaViewModel.uploadData()

                findNavController().navigate(R.id.action_loginScreen_to_cancelOrder)
            }
            // USER HAS LOGGED IN ALREADY JUST GET PHONE AND ORDER INFO TO FIREBASE, CRASH ON SENDING ORDER
            else if (bubbleTeaViewModel.loggedIn.value == true && phone_texteditL.text.isNotBlank()) {
                val currentDate = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(Date())
                val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                var time = currentDate + " " + currentTime

                bubbleTeaViewModel.phone.value = phone_texteditL.text.toString()

                // get info from local database
                var personalInfo = bubbleTeaViewModel.getInfoByPhone(phone_texteditL.text.toString())
                if(personalInfo != null) {
                    println("!!!!!!!!!!!!!!!!!!!!!!!!!!")
                    bubbleTeaViewModel.currentTime.value = time
                    bubbleTeaViewModel.name.value = personalInfo.name
                    bubbleTeaViewModel.address.value = personalInfo.address
                    bubbleTeaViewModel.uploadData()
                    findNavController().navigate(R.id.action_loginScreen_to_cancelOrder)
                }else{
                    Toast.makeText(context, "You Have Not Log In Yet", Toast.LENGTH_LONG).show()
                }




            } else {
                Toast.makeText(context, "Missing Information", Toast.LENGTH_LONG).show()
            }


        }
    }


}
