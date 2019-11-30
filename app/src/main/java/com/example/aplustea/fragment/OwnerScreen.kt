package com.example.aplustea.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplustea.BubbleTeaViewModel
import com.example.aplustea.R
import com.example.aplustea.adapter.RecyclerViewAdapterAccountScreen
import com.example.aplustea.adapter.RecyclerViewAdapterOwnerScreen
import kotlinx.android.synthetic.main.fragment_account_screen.*
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class OwnerScreen : Fragment() {
    lateinit var bubbleTeaViewModel: BubbleTeaViewModel
    lateinit var viewAdapter: RecyclerViewAdapterOwnerScreen
    lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bubbleTeaViewModel = activity?.run {
            ViewModelProviders.of(this).get(BubbleTeaViewModel::class.java)
        } ?: throw Exception("activity invalid")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_owner_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewAdapter = RecyclerViewAdapterOwnerScreen(ArrayList())
        viewManager = LinearLayoutManager(context)

        account_screen_recyclerview.apply {
            this.adapter = viewAdapter
            this.layoutManager = viewManager
        }


        bubbleTeaViewModel.allOrderInfo.observe(this, Observer {
            viewAdapter.allOrderInfo = it
            viewAdapter.notifyDataSetChanged()
        })
    }
}
