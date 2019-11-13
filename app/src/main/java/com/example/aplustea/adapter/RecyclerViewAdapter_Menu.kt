package com.example.aplustea.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplustea.MenuItem
import com.example.aplustea.R
import kotlinx.android.synthetic.main.fragment_home_screen.view.*
import kotlinx.android.synthetic.main.menu_item.view.*

class RecyclerViewAdapter_Menu(
    var menuItem: ArrayList<MenuItem>,
    val clickListener: (MenuItem) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapter_Menu.RecyclerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val viewItem =
            LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return RecyclerViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return menuItem.size
    }


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(menuItem[position], clickListener)
    }

    inner class RecyclerViewHolder(var viewItem: View) : RecyclerView.ViewHolder(viewItem) {

        fun bind(item: MenuItem, clickListener: (MenuItem) -> Unit) {
            viewItem.menuItem.setBackgroundResource(item.backGround)
            viewItem.menuItem.text = item.name


            viewItem.setOnClickListener { clickListener(item) }
        }
    }
}