package com.example.aplustea.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplustea.CartScreenItem
import com.example.aplustea.R
import kotlinx.android.synthetic.main.cart_item.view.*
import kotlinx.android.synthetic.main.fragment_cart_screen.view.*

class RecyclerViewAdapterCartScreen(
    var cart_array: ArrayList<CartScreenItem>
) : RecyclerView.Adapter<RecyclerViewAdapterCartScreen.RecyclerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val viewItem =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return RecyclerViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return cart_array.size
    }


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(cart_array[position])
    }

    inner class RecyclerViewHolder(var viewItem: View) : RecyclerView.ViewHolder(viewItem) {

        fun bind(item: CartScreenItem) {
            viewItem.bubbleTeaCartView.setImageResource(item.itemPicture)
            viewItem.flavor_name.setText(item.flavor)
            var preference = item.size + "/" + item.sweetness + "/" + item.temperature + "/"
            viewItem.personal_preference.setText(preference)
            viewItem.unit_price.setText(item.unitPrice.toString() + "$")
            viewItem.quantity_text_cart.setText(item.quantity.toString())
        }
    }
}