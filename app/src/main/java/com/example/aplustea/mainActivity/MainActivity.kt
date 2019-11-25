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
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(findViewById(R.id.myToolbar))
        val navController = findNavController(R.id.nav_host_frag)
        findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(navController)
        //myToolbar.setupWithNavController(navController)

        bottom_nav.setOnNavigationItemSelectedListener {
            if (it.itemId == R.id.cart) {
                findNavController(R.id.nav_host_frag).navigate(R.id.action_global_cartScreen)
            }
            else if (it.itemId == R.id.profile) {
                findNavController(R.id.nav_host_frag).navigate(R.id.action_global_accountScreen)
            }
            else if (it.itemId == R.id.menuMenu) {
                findNavController(R.id.nav_host_frag).navigate(R.id.action_global_menuScreen)
            }

            true

        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_bottom_nav, menu)
        return true

    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        val itemId = item.itemId
//
//        if (itemId == R.id.profile) {
//            findNavController(R.id.nav_host_frag).navigate(R.id.action_global_accountScreen)
//        }
//        return super.onOptionsItemSelected(item)
//    }

}
