package com.example.aplustea.mainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.aplustea.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(findViewById(R.id.myToolbar))

        val navController = findNavController(R.id.nav_host_frag)
        myToolbar.setupWithNavController(navController)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.action_accountScreen -> findNavController(R.id.nav_host_frag).navigate(R.id.action_global_accountScreen)
        }
        when (item?.itemId) {
            R.id.action_favoritesScreen -> findNavController(R.id.nav_host_frag).navigate(R.id.action_global_favoritesScreen)
        }
        when (item?.itemId) {
            R.id.action_previousScreen -> findNavController(R.id.nav_host_frag).navigate(R.id.action_global_previousOrdersScreen)
        }
        return true

    }

}
