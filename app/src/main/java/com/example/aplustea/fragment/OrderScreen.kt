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
import android.widget.Button
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.aplustea.CartScreenItem
import kotlinx.android.synthetic.*

/**
 * A simple [Fragment] subclass.
 */
class OrderScreen : Fragment() {
    lateinit var bubbleTeaViewModel: BubbleTeaViewModel
    val pearlsTypes = arrayOf("Regular Boba", "Popping Boba")

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

        bubbleTeaViewModel.sweetnessRadioGroupID.value =
            sweetness_radioGroup.checkedRadioButtonId
        bubbleTeaViewModel.temperatureRadioGroupID.value =
            temperature_radioGroup.checkedRadioButtonId
        bubbleTeaViewModel.sizeRadioGroupID.value = _radioGroup.checkedRadioButtonId
        bubbleTeaViewModel.quantityString.value = quantity_editText.text.toString()
        bubbleTeaViewModel.pearlsSpinnerPosition.value = boba_spinner.selectedItemPosition

    }

    override fun onResume() { //RESUME HERE NOT IN onViewCreated
        super.onResume()
        bubbleTeaViewModel.quantityString.observe(this, Observer {
            quantity_editText.setText(it)
        })
        bubbleTeaViewModel.sweetnessRadioGroupID.observe(this, Observer {
            if (it == R.id.regular_sweetness_button || it == R.id.very_sweetness_button)
            sweetness_radioGroup.check(it)
        })
        bubbleTeaViewModel.temperatureRadioGroupID.observe(this, Observer {
            if (it == R.id.hot_button || it == R.id.cold_button)
            temperature_radioGroup.check(it)
        })
        bubbleTeaViewModel.sizeRadioGroupID.observe(this, Observer {
            if (it == R.id.large_size_button || it == R.id.regular_size_button)
            _radioGroup.check(it)
        })
        bubbleTeaViewModel.pearlsSpinnerPosition.observe(this, Observer {
            boba_spinner.setSelection(it)
        })

        //show the current bubble tea picture
        bubbleTeaViewModel.bubbleTeaTypePicture.observe(this, Observer {
            bubbleTeaView.setImageResource(it)
        })
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        bubbleTeaViewModel.orderSwitchButton.value = true

        // adapter for spinner
        val pearlsAdapter =
            ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, pearlsTypes)
        boba_spinner.adapter = pearlsAdapter

        //IMPORTANT : You can not resume here, this is only called ONCE

        // resume the work
//        bubbleTeaViewModel.quantityString.observe(this, Observer {
//            quantity_editText.setText(it)
//        })
//        bubbleTeaViewModel.sweetnessRadioGroupID.observe(this, Observer {
//            sweetness_radioGroup.check(it)
//        })
//        bubbleTeaViewModel.temperatureRadioGroupID.observe(this, Observer {
//            temperature_radioGroup.check(it)
//        })
//        bubbleTeaViewModel.sizeRadioGroupID.observe(this, Observer {
//            size_radioGroup.check(it)
//        })
//        bubbleTeaViewModel.pearlsSpinnerPosition.observe(this, Observer {
//            boba_spinner.setSelection(it)
//        })
//
//        //show the current bubble tea picture
//        bubbleTeaViewModel.bubbleTeaTypePicture.observe(this, Observer {
//            bubbleTeaView.setImageResource(it)
//        })


        // show the total price of the current type bubble tea
        bubbleTeaViewModel.bubbleTeaUnitPrice.observe(this, Observer {
            item_total_textview.setText("$$it")
            // this will get the text immediately when the quantity get changed
            quantity_editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (quantity_editText.text.toString() != "") {
                        var price = it * quantity_editText.text.toString().toDouble()
                        item_total_textview.setText("$$price")
                    } else {
                        item_total_textview.setText("$$it")
                    }
                }
            })
        })
        add_button.setOnClickListener {
            // add to cartScreen arraylist if it is not in there
            var cartScreenItem: CartScreenItem = addCartItem()
            var duplicates = false
            bubbleTeaViewModel.cartScreenItem.value!!.forEach {
                if (it.flavor == cartScreenItem.flavor &&
                    it.itemPicture == cartScreenItem.itemPicture &&
                    it.quantity == cartScreenItem.quantity &&
                    it.size == cartScreenItem.size &&
                    it.sweetness == cartScreenItem.sweetness &&
                    it.temperature == cartScreenItem.temperature &&
                    it.pearls == cartScreenItem.pearls
                ) {
                    duplicates = true
                }
            }
            if (duplicates == false && quantity_editText.text.toString() != "") {
                var currentPrice = bubbleTeaViewModel.totalPrice.value!!
                bubbleTeaViewModel.totalPrice.value =
                    currentPrice + quantity_editText.text.toString().toDouble() * bubbleTeaViewModel.bubbleTeaUnitPrice.value!!
                bubbleTeaViewModel.cartScreenItem.value!!.add(cartScreenItem)
            }
        }
        checkout_button.setOnClickListener {
            // add to cartScreen arraylist if it is not in there
            var cartScreenItem: CartScreenItem = addCartItem()
            var duplicates = false
            bubbleTeaViewModel.cartScreenItem.value!!.forEach {
                if (it.flavor == cartScreenItem.flavor &&
                    it.itemPicture == cartScreenItem.itemPicture &&
                    it.quantity == cartScreenItem.quantity &&
                    it.size == cartScreenItem.size &&
                    it.sweetness == cartScreenItem.sweetness &&
                    it.temperature == cartScreenItem.temperature &&
                    it.pearls == cartScreenItem.pearls
                ) {
                    duplicates = true
                }
            }
            if (duplicates == false && quantity_editText.text.toString() != "") {
                var currentPrice = bubbleTeaViewModel.totalPrice.value!!
                bubbleTeaViewModel.totalPrice.value =
                    currentPrice + quantity_editText.text.toString().toDouble() * bubbleTeaViewModel.bubbleTeaUnitPrice.value!!
                bubbleTeaViewModel.cartScreenItem.value!!.add(cartScreenItem)
            }
            findNavController().navigate(R.id.action_orderScreen_to_cartScreen)
            //quantity_editText.text.clear()
            //sweetness_radioGroup.clearCheck()
            //temperature_radioGroup.clearCheck()
            //size_radioGroup.clearCheck()

        }

        boba_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                boba_spinner.isVisible = true
                boba_type_string.isVisible = true
                bubbleTeaViewModel.orderSwitchButton.value = true
            } else {
                boba_spinner.isVisible = false
                boba_type_string.isVisible = false
                bubbleTeaViewModel.orderSwitchButton.value = false
            }
        }


    }

    // get current cart item
    fun addCartItem(): CartScreenItem {
        // get temperature string

        var selectedRadioButtonID = temperature_radioGroup.checkedRadioButtonId
        var tempButtonString = ""
        when (selectedRadioButtonID) {
            R.id.hot_button -> tempButtonString = hot_button.text.toString()
            R.id.cold_button -> tempButtonString = cold_button.text.toString()
        }

        // get size string
        selectedRadioButtonID = _radioGroup.checkedRadioButtonId
        var sizeButtonString = ""
        when (selectedRadioButtonID) {
            R.id.regular_size_button -> sizeButtonString = regular_size_button.text.toString()
            R.id.large_size_button -> sizeButtonString = large_size_button.text.toString()
        }

        // get sweetness string
        selectedRadioButtonID = sweetness_radioGroup.checkedRadioButtonId
        var sweetnessButtonString = ""
        when (selectedRadioButtonID) {
            R.id.regular_sweetness_button -> sweetnessButtonString =
                regular_sweetness_button.text.toString()
            R.id.very_sweetness_button -> sweetnessButtonString =
                very_sweetness_button.text.toString()
        }

        // if quantity string is not empty then return the item else return an empty item
        if (quantity_editText.text.toString() != "" && bubbleTeaViewModel.orderSwitchButton.value == true) {
            return CartScreenItem(
                bubbleTeaViewModel.bubbleTeaType.value!!,
                bubbleTeaViewModel.bubbleTeaTypePicture.value!!,
                tempButtonString,
                sizeButtonString,
                sweetnessButtonString,
                bubbleTeaViewModel.bubbleTeaUnitPrice.value!!,
                quantity_editText.text.toString().toInt(),
                boba_spinner.selectedItem.toString()
            )
        } else if (quantity_editText.text.toString() != "" && bubbleTeaViewModel.orderSwitchButton.value == false) {
            return CartScreenItem(
                bubbleTeaViewModel.bubbleTeaType.value!!,
                bubbleTeaViewModel.bubbleTeaTypePicture.value!!,
                tempButtonString,
                sizeButtonString,
                sweetnessButtonString,
                bubbleTeaViewModel.bubbleTeaUnitPrice.value!!,
                quantity_editText.text.toString().toInt(),
                "No Boba"
            )
        } else {
            return CartScreenItem("", 0, "", "", "", 0.0, 0, "")
        }
    }
}