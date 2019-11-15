package com.example.aplustea.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.aplustea.BubbleTeaViewModel
import com.example.aplustea.R
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class OrderScreen : Fragment() {
    lateinit var bubbleTeaViewModel: BubbleTeaViewModel
//    val sizeTypes = arrayOf("Small", "Medium", "Large")
//    val sweetnessTypes = arrayOf("None", "Medium", "Very")
//    val temperatureTypes = arrayOf("Cold", "Hot")
    val pearlsTypes = arrayOf("Yes", "No")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bubbleTeaViewModel = activity?.run{
            ViewModelProviders.of(this).get(BubbleTeaViewModel::class.java)
        }?:throw Exception("activity invalid")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_screen, container, false)
    }

    override fun onPause() {
        super.onPause()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }


}