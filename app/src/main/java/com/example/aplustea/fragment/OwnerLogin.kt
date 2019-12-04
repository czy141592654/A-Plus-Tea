package com.example.aplustea.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.aplustea.BubbleTeaViewModel
import com.example.aplustea.R
import kotlinx.android.synthetic.main.fragment_owner_login.*
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class OwnerLogin : Fragment() {
    lateinit var bubbleTeaViewModel: BubbleTeaViewModel
    var password = 123456789

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bubbleTeaViewModel = activity?.run {
            ViewModelProviders.of(this).get(BubbleTeaViewModel::class.java)
        } ?: throw Exception("activity invalid")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_owner_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        login_buttonO.setOnClickListener {
            if (owner_code_editTextO.text.toString() == password.toString()) {
                bubbleTeaViewModel.isOwner.value = true
                ownerText.text = "You have successfully logged in as an owner. Please check the profile page."
            }
            else {
                ownerText.text = "You have failed to login as the owner. Please enter the correct owner code."
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (bubbleTeaViewModel.isOwner.value == true) {
            ownerText.text = "You have successfully logged in as an owner. Please check the profile page."
        }
        else {
            ownerText.text = "Please login with the owner code only if, you are the owner."
        }
    }


}
