package com.example.aplustea.mainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
//import androidx.navigation.Navigation.findNavController
//import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
//import androidx.navigation.findNavController
import com.example.aplustea.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.myToolbar))
        //val navController = findNavController(R.id.nav_host_frag)
        //myToolbar.setupWithNavController(navController)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val itemId = item.itemId

        if (itemId == R.id.accountScreen_item) {
            findNavController(R.id.nav_host_frag).navigate(R.id.action_global_accountScreen)
        }
        else if (itemId == R.id.favoritesScreen_item) {
            findNavController(R.id.nav_host_frag).navigate(R.id.action_global_favoritesScreen)
        }
        else if (itemId == R.id.previousScreen_item) {
            findNavController(R.id.nav_host_frag).navigate(R.id.action_global_previousOrdersScreen)
        }
        return super.onOptionsItemSelected(item)
    }

}
