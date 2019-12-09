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
import com.example.aplustea.Bubble
import com.example.aplustea.BubbleTeaViewModel
import com.example.aplustea.Item
import com.example.aplustea.R
import com.example.aplustea.adapter.RecyclerViewAdapterBubble
import com.example.aplustea.adapter.RecyclerViewAdapterMenu
import kotlinx.android.synthetic.main.fragment_menu_screen.*
import java.lang.Exception


/**
 * A simple [Fragment] subclass.
 */
class MenuScreen : Fragment() {
    var itemArray = ArrayList<Item>()
    var bubbleArray = ArrayList<Bubble>()
    lateinit var viewAdapterMenu: RecyclerViewAdapterMenu
    lateinit var viewManagerMenu: RecyclerView.LayoutManager
    lateinit var viewAdapterBubble: RecyclerViewAdapterBubble
    lateinit var viewManagerBubble: RecyclerView.LayoutManager
    lateinit var bubbleTeaViewModel:BubbleTeaViewModel
    //var loseSound: MediaPlayer = MediaPlayer.create(con, R.raw.losing)


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
        anouncements.text = "Business hour: 12:00 - 9:00" + "\n" + "You can call 774-2812577 to cancel the order" + "\n" + "Delivary only 5 minutes walk from Clark"
        // recycler view adapter for menu
        viewAdapterMenu = RecyclerViewAdapterMenu(itemArray) { menuitem:Item ->
            recyclerViewItemSelected(menuitem)
        }

        viewManagerMenu = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        menu_recyclerView.apply {
            this.adapter = viewAdapterMenu
            this.layoutManager = viewManagerMenu
        }

        // recycler view adapter for bubble
        viewAdapterBubble = RecyclerViewAdapterBubble(bubbleArray)
        viewManagerBubble = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        bubble_recyclerView.apply {
            this.adapter = viewAdapterBubble
            this.layoutManager = viewManagerBubble
        }
    }

    override fun onResume() {
        super.onResume()
        itemArray.clear()
        bubbleArray.clear()
        anouncements.text = " Business hour: 12:00 - 9:00" + "\n" + " Contact: 774-2812577 " + "\n" + " Delivery On Clark Campus"
        itemArray.add(Item(R.drawable.original_flavor,"Original Flavor",3.0))
        itemArray.add(Item(R.drawable.matcha,"Matcha",4.0))
        itemArray.add(Item(R.drawable.vanilla,"Vanilla", 3.5))
        bubbleArray.add(Bubble(R.drawable.boba_black,"Regular Boba"))
        bubbleArray.add(Bubble(R.drawable.bursting_boba,"Bursting Boba"))

    }

    fun recyclerViewItemSelected(menuItem: Item){
        bubbleTeaViewModel.bubbleTeaType.value = menuItem.name
        bubbleTeaViewModel.bubbleTeaTypePicture.value = menuItem.backGround
        bubbleTeaViewModel.bubbleTeaUnitPrice.value = menuItem.price
        findNavController().navigate(R.id.action_menuScreen_to_orderScreen)
    }

}
