package com.example.aplustea.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.aplustea.R
import kotlinx.android.synthetic.main.fragment_confirmed_order.*

/**
 * A simple [Fragment] subclass.
 */
class ConfirmedOrder : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirmed_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        yes_button.setOnClickListener {
            findNavController().navigate(R.id.action_confirmedOrder_to_cancelOrder)
        }
        no_button.setOnClickListener {
            findNavController().navigate(R.id.action_confirmedOrder_to_menuScreen)
        }
    }


}
