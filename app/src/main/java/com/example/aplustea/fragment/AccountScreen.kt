package com.example.aplustea.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplustea.BubbleTeaViewModel
import com.example.aplustea.PersonalInfo
import com.example.aplustea.R
import com.example.aplustea.adapter.RecyclerViewAdapterAccountScreen
import com.example.aplustea.adapter.RecyclerViewAdapterCartScreen
import kotlinx.android.synthetic.main.fragment_account_screen.*
import kotlinx.android.synthetic.main.fragment_cart_screen.*
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class AccountScreen : Fragment() {
    lateinit var bubbleTeaViewModel: BubbleTeaViewModel
    lateinit var viewAdapter: RecyclerViewAdapterAccountScreen
    lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bubbleTeaViewModel = activity?.run {
            ViewModelProviders.of(this).get(BubbleTeaViewModel::class.java)
        } ?: throw Exception("activity invalid")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        name_editTextA.setText(bubbleTeaViewModel.name.value)
        address_editTextA.setText(bubbleTeaViewModel.address.value)
        phone_texteditA.setText(bubbleTeaViewModel.phone.value)

        update_button.setOnClickListener {
            bubbleTeaViewModel.deleteInfo(PersonalInfo(bubbleTeaViewModel.phone.value!!,bubbleTeaViewModel.name.value!!,bubbleTeaViewModel.address.value!!))
            bubbleTeaViewModel.insertInfo(PersonalInfo(phone_texteditA.text.toString(),name_editTextA.text.toString(),address_editTextA.text.toString()))
            Toast.makeText(context, "updated", Toast.LENGTH_LONG).show()
        }

        viewAdapter = RecyclerViewAdapterAccountScreen(ArrayList())
        viewManager = LinearLayoutManager(context)

        account_screen_recyclerview.apply {
            this.adapter = viewAdapter
            this.layoutManager = viewManager
        }


        bubbleTeaViewModel.userOrderInfo.observe(this, Observer {
            viewAdapter.orderInfo_array = it
            viewAdapter.notifyDataSetChanged()
        })
    }

}
