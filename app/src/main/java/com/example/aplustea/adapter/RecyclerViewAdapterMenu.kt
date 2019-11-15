package com.example.aplustea.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplustea.Item
import com.example.aplustea.R
import kotlinx.android.synthetic.main.menu_item.view.*

class RecyclerViewAdapterMenu(
    var menu_array: ArrayList<Item>,
    val clickListener: (Item) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapterMenu.RecyclerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val viewItem =
            LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return RecyclerViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return menu_array.size
    }


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(menu_array[position], clickListener)
    }

    inner class RecyclerViewHolder(var viewItem: View) : RecyclerView.ViewHolder(viewItem) {

        fun bind(item: Item, clickListener: (Item) -> Unit) {
            viewItem.menuItem.setBackgroundResource(item.backGround)
            viewItem.menuItem.text = item.name


            viewItem.setOnClickListener { clickListener(item) }
        }
    }
}