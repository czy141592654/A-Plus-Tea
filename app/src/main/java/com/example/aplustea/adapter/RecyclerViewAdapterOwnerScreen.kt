package com.example.aplustea.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplustea.R
import com.example.aplustea.UserOrOwnerInfo
import kotlinx.android.synthetic.main.user_order_info_item.view.*

class RecyclerViewAdapterOwnerScreen(
    var allOrderInfo: ArrayList<UserOrOwnerInfo>
) : RecyclerView.Adapter<RecyclerViewAdapterOwnerScreen.RecyclerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val viewItem =
            LayoutInflater.from(parent.context).inflate(R.layout.owner_screen_item, parent, false)
        return RecyclerViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return allOrderInfo.size
    }


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(allOrderInfo[position])
    }

    inner class RecyclerViewHolder(var viewItem: View) : RecyclerView.ViewHolder(viewItem) {

        fun bind(item: UserOrOwnerInfo) {
            viewItem.user_order_info_account_screen.setText(item.info)

        }
    }
}