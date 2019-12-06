package com.example.aplustea.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplustea.Bubble
import com.example.aplustea.CartScreenItem
import com.example.aplustea.R
import kotlinx.android.synthetic.main.bubble_item.view.*
import kotlinx.android.synthetic.main.bubble_item.view.menuItemText
import kotlinx.android.synthetic.main.cart_item.view.*
import kotlinx.android.synthetic.main.fragment_cart_screen.view.*
import kotlinx.android.synthetic.main.menu_item.view.*

class RecyclerViewAdapterBubble(
    var bubble_array: ArrayList<Bubble>
) : RecyclerView.Adapter<RecyclerViewAdapterBubble.RecyclerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val viewItem =
            LayoutInflater.from(parent.context).inflate(R.layout.bubble_item, parent, false)
        return RecyclerViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return bubble_array.size
    }


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(bubble_array[position])
    }

    inner class RecyclerViewHolder(var viewItem: View) : RecyclerView.ViewHolder(viewItem) {

        fun bind(item: Bubble) {
            viewItem.bubble.setBackgroundResource(item.image)
            viewItem.menuItemText.text = item.name
        }
    }
}