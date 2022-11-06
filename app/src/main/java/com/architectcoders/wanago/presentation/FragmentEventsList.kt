package com.architectcoders.wanago.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.wanago.data.repository.EventRepository
import com.architectcoders.wanago.databinding.FragmentEventsListBinding
import com.architectcoders.wanago.domain.model.Event

class FragmentEventsList : Fragment() {

    private var _binding: FragmentEventsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var eventsAdapter: EventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = EventRepository

        val layoutManager = LinearLayoutManager(context)
        eventsAdapter = EventsAdapter(repository.getEvents())
        binding.recyclerEventsList.apply {
            this.layoutManager = layoutManager
            this.setHasFixedSize(true)
            this.adapter = eventsAdapter
        }
    }


}

