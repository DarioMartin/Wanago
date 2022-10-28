package com.architectcoders.wanago

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.architectcoders.wanago.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerEventsList.adapter = EventsAdapter(listOf(
            Event("https://loremflickr.com/320/240?lock=1", "Drake","Sevilla"),
            Event("https://loremflickr.com/320/240?lock=", "Drake","Madrid"),
            Event("https://loremflickr.com/320/240?lock=3", "Drake","Barcelona"),
            Event("https://loremflickr.com/320/240?lock=4", "Drake","Oviedo"),
            Event("https://loremflickr.com/320/240?lock=5", "Drake","Valencia")
        )
        )
    }
}