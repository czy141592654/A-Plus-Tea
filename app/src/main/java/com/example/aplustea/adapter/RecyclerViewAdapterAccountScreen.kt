package com.example.aplustea.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplustea.R
import com.example.aplustea.UserOrderInfo
import kotlinx.android.synthetic.main.user_order_info_item.view.*

class RecyclerViewAdapterAccountScreen(
    var orderInfo_array: ArrayList<UserOrderInfo>
) : RecyclerView.Adapter<RecyclerViewAdapterAccountScreen.RecyclerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val viewItem =
            LayoutInflater.from(parent.context).inflate(R.layout.user_order_info_item, parent, false)
        return RecyclerViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return orderInfo_array.size
    }


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(orderInfo_array[position])
    }

    inner class RecyclerViewHolder(var viewItem: View) : RecyclerView.ViewHolder(viewItem) {

        fun bind(item: UserOrderInfo) {
            viewItem.user_order_info_account_screen.setText(item.info)

        }
    }
}