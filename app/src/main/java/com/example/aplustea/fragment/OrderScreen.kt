package com.example.aplustea.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.aplustea.BubbleTeaViewModel
import com.example.aplustea.R
import kotlinx.android.synthetic.main.fragment_order_screen.*
import java.lang.Exception
import android.text.TextWatcher

/**
 * A simple [Fragment] subclass.
 */
class OrderScreen : Fragment() {
    lateinit var bubbleTeaViewModel: BubbleTeaViewModel
    val pearlsTypes = arrayOf("Yes", "No")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bubbleTeaViewModel = activity?.run {
            ViewModelProviders.of(this).get(BubbleTeaViewModel::class.java)
        } ?: throw Exception("activity invalid")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_screen, container, false)
    }

    override fun onPause() {
        // save the current work
        super.onPause()
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + sweetness_radioGroup.checkedRadioButtonId )
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + size_radioGroup.checkedRadioButtonId )
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + quantity_editText.text.toString() )
        bubbleTeaViewModel.sweetnessRadioGroupID.value =
            sweetness_radioGroup.checkedRadioButtonId
        bubbleTeaViewModel.temperatureRadioGroupID.value =
            temperature_radioGroup.checkedRadioButtonId
        bubbleTeaViewModel.sizeRadioGroupID.value = size_radioGroup.checkedRadioButtonId
        bubbleTeaViewModel.quantityString.value = quantity_editText.text.toString()
        bubbleTeaViewModel.pearlsSpinnerPosition.value = pearls_spinner.selectedItemPosition
        println("///////////////////////////////////////" + bubbleTeaViewModel.sweetnessRadioGroupID.value )
        println("///////////////////////////////////////" + bubbleTeaViewModel.sizeRadioGroupID.value )
        println("///////////////////////////////////////" + bubbleTeaViewModel.quantityString.value )
    }



    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // adapter for spinner
        val pearlsAdapter =
            ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, pearlsTypes)
        pearls_spinner.adapter = pearlsAdapter
        println("---------------------------------------" + bubbleTeaViewModel.sweetnessRadioGroupID.value )
        println("---------------------------------------" + bubbleTeaViewModel.sizeRadioGroupID.value )
        println("---------------------------------------" + bubbleTeaViewModel.quantityString.value  )
        // resume the work
        bubbleTeaViewModel.quantityString.observe(this, Observer {
            quantity_editText.setText(it)
        })
        bubbleTeaViewModel.sweetnessRadioGroupID.observe(this, Observer {
            sweetness_radioGroup.check(it)
        })
        bubbleTeaViewModel.temperatureRadioGroupID.observe(this, Observer {
            temperature_radioGroup.check(it)
        })
        bubbleTeaViewModel.sizeRadioGroupID.observe(this, Observer {
            size_radioGroup.check(it)
        })
        bubbleTeaViewModel.pearlsSpinnerPosition.observe(this, Observer {
            pearls_spinner.setSelection(it)
        })

        //show the current bubble tea picture
        bubbleTeaViewModel.bubbleTeaTypePicture.observe(this, Observer {
            bubbleTeaView.setImageResource(it)
        })


        // show the total price of the current type bubble tea
        bubbleTeaViewModel.bubbleTeaPrice.observe(this, Observer {
            item_total_textview.setText("$it$")
            // this will get the text immediately when the quantity get changed
            quantity_editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (quantity_editText.text.toString() != "") {
                        var price = it * quantity_editText.text.toString().toDouble()
                        item_total_textview.setText("$price$")
                    } else {
                        item_total_textview.setText("$it$")
                    }
                }
            })
        })

    }
}