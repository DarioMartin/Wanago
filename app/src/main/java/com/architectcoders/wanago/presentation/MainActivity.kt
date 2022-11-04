package com.architectcoders.wanago.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.architectcoders.wanago.R
import com.architectcoders.wanago.databinding.ActivityMainBinding
import com.architectcoders.wanago.domain.model.Event
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), EventListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fragment_container_view)

    }

    override fun onEventSelected(event: Event) {
        val action = FragmentEventsListDirections.actionFragmentEventsListToEventDetailsFragment()
        navController.navigate(action)
    }

}