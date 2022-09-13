package io.h3llo.appmarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import io.h3llo.appmarket.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater )
        setContentView(binding.root)

        init()
    }

    private fun init(){
       val navController = Navigation.findNavController(this, R.id.nav_host_fragment_menu)
        NavigationUI.setupWithNavController(binding.navigationView,navController)

        binding.imgMenu.setOnClickListener{
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}