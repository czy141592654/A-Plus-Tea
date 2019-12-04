package com.example.aplustea.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.aplustea.BubbleTeaViewModel
import com.example.aplustea.R
import kotlinx.android.synthetic.main.fragment_cancel_order.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class CancelOrder : Fragment() {
    lateinit var bubbleTeaViewModel:BubbleTeaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bubbleTeaViewModel = activity?.run {
            ViewModelProviders.of(this).get(BubbleTeaViewModel::class.java)
        } ?: throw Exception("activity invalid")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cancel_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        yes_button.setOnClickListener {

            val currentDate =
                SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(Date())
            val currentTime =
                SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            var time = currentDate + " " + currentTime
            bubbleTeaViewModel.currentTime.value = time
            bubbleTeaViewModel.cartScreenItem.value!!.forEach {
                bubbleTeaViewModel.cartStrings.value!!.add(
                    bubbleTeaViewModel.pickupordeliver.value.toString() + "  " + ("$" + it.quantity * it.unitPrice).toString() + "  " + it.flavor + "  " + it.size + "  " + it.sweetness + "  " + it.temperature + "  " + it.pearls
                )
            }
            bubbleTeaViewModel.uploadData()
            bubbleTeaViewModel.cartScreenItem.value!!.clear()
            bubbleTeaViewModel.totalPrice.value = 0.0
            findNavController().navigate(R.id.action_cancelOrder_to_accountScreen)
        }
        no_button.setOnClickListener {
            Toast.makeText(context, "Your order has been canceled.", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_cancelOrder_to_cartScreen)
        }
    }


}
