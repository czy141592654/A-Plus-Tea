package com.example.aplustea.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplustea.BubbleTeaViewModel
import com.example.aplustea.Item
import com.example.aplustea.R
import com.example.aplustea.adapter.RecyclerViewAdapterMenu
import kotlinx.android.synthetic.main.fragment_menu_screen.*
import kotlinx.android.synthetic.main.menu_item.*
import java.lang.Exception


/**
 * A simple [Fragment] subclass.
 */
class MenuScreen : Fragment() {
    var itemArray = ArrayList<Item>()
    lateinit var viewAdapter: RecyclerViewAdapterMenu
    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var bubbleTeaViewModel:BubbleTeaViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bubbleTeaViewModel = activity?.run {
            ViewModelProviders.of(this).get(BubbleTeaViewModel::class.java)
        }?:throw Exception("activity invalid")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        itemArray.add(Item(R.drawable.original_flavor,"Original Flavor",3.0))

        viewAdapter = RecyclerViewAdapterMenu(itemArray) { menuitem:Item ->
            recyclerViewItemSelected(menuitem)
        }

        viewManager = LinearLayoutManager(context)

        menu_recyclerView.apply {
            this.adapter = viewAdapter
            this.layoutManager = viewManager
        }
    }

    fun recyclerViewItemSelected(menuItem: Item){
        bubbleTeaViewModel.bubbleTeaType.value = menuItem.name
        bubbleTeaViewModel.bubbleTeaTypePicture.value = menuItem.backGround
        bubbleTeaViewModel.bubbleTeaPrice.value = menuItem.price
        findNavController().navigate(R.id.action_menuScreen_to_orderScreen)
    }

}
