package com.example.aplustea.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplustea.BubbleTeaViewModel
import com.example.aplustea.R
import com.example.aplustea.adapter.RecyclerViewAdapterCartScreen
import kotlinx.android.synthetic.main.fragment_cart_screen.*
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class CartScreen : Fragment() {
    lateinit var bubbleTeaViewModel: BubbleTeaViewModel
    lateinit var viewAdapter: RecyclerViewAdapterCartScreen
    lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bubbleTeaViewModel = activity?.run {
            ViewModelProviders.of(this).get(BubbleTeaViewModel::class.java)
        } ?: throw Exception("activity invalid")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // button for delivery or pick up


        // for cart recycler view
        viewAdapter = RecyclerViewAdapterCartScreen(ArrayList())
        viewManager = LinearLayoutManager(context)

        cart_screen_recyclerview.apply {
            this.adapter = viewAdapter
            this.layoutManager = viewManager
        }


        bubbleTeaViewModel.cartScreenItem.observe(this, Observer {
            viewAdapter.cart_array = it
            viewAdapter.notifyDataSetChanged()
        })

        add_more_button.setOnClickListener {
            findNavController().navigate(R.id.action_cartScreen_to_menuScreen)
        }

        bubbleTeaViewModel.totalPrice.observe(this, Observer {
            total_price_text.setText("$" + it.toString())
        })

        ItemTouchHelper(SwiperHelper()).attachToRecyclerView(
            cart_screen_recyclerview
        )

        place_order_button.setOnClickListener {
            if (total_price_text.text.toString() != "$0.0") { // Changed no longer checks login
                when (size_radioGroup.checkedRadioButtonId) {
                    R.id.pickup_button -> bubbleTeaViewModel.pickupordeliver.value =
                        pickup_button.text.toString()
                    R.id.delivery_button -> bubbleTeaViewModel.pickupordeliver.value =
                        delivery_button.text.toString()
                }
//                for (CartScreenItem in viewAdapter.cart_array) {
//                    bubbleTeaViewModel.cartStrings.value?.add(
//                        bubbleTeaViewModel.pickupordeliver.value.toString() + "  " + ("$" + CartScreenItem.quantity*CartScreenItem.unitPrice).toString() + "  " + CartScreenItem.flavor + "  " + CartScreenItem.size + "  " + CartScreenItem.sweetness + "  " + CartScreenItem.temperature + "  " + CartScreenItem.pearls)
//                }
                viewAdapter.cart_array.forEach {
                    bubbleTeaViewModel.cartStrings.value!!.add(
                        bubbleTeaViewModel.pickupordeliver.value.toString() + "  " + ("$" + it.quantity * it.unitPrice).toString() + "  " + it.flavor + "  " + it.size + "  " + it.sweetness + "  " + it.temperature + "  " + it.pearls
                    )
                }
                viewAdapter.cart_array.clear()
                bubbleTeaViewModel.totalPrice.value = 0.0
                findNavController().navigate(R.id.action_cartScreen_to_loginScreen) // will always go to login screen
            } else {
                Toast.makeText(context, "Cart is Empty. Please order something!", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(context, "Swipe right to delete order items", Toast.LENGTH_LONG).show()
    }

    // swipe right to delete
    inner class SwiperHelper() : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            var item = bubbleTeaViewModel.cartScreenItem.value!!.get(viewHolder.adapterPosition)
            var deleteItemPrice = item.unitPrice * item.quantity.toDouble()
            bubbleTeaViewModel.totalPrice.value =
                bubbleTeaViewModel.totalPrice.value!!.minus(deleteItemPrice)
            bubbleTeaViewModel.cartScreenItem.value!!.remove(item)
            viewAdapter.notifyItemRemoved(viewHolder.adapterPosition)
        }
    }
}

