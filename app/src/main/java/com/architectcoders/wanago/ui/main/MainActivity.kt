package com.architectcoders.wanago.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.architectcoders.wanago.R
import com.architectcoders.wanago.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(FragmentEventsList())
    }

    private fun replaceFragment(fragmentEventsList: FragmentEventsList) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragmentEventsList)
        fragmentTransaction.commit()
    }
}

