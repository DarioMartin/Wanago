package com.architectcoders.wanago.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import arrow.core.right
import com.architectcoders.wanago.App
import com.architectcoders.wanago.data.EventsRepository
import com.architectcoders.wanago.databinding.FragmentEventsListBinding
import com.architectcoders.wanago.domain.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FragmentEventsList : Fragment(), App.RepositoryListener {

    private var _binding:FragmentEventsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var eventsAdapter : EventsAdapter
    private lateinit var recyclerView: RecyclerView
    private var eventList : List<Event> = listOf()
    private lateinit var app: App


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsListBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind()

        val layoutManager = LinearLayoutManager(context)
        eventsAdapter = EventsAdapter(eventList)
        recyclerView = binding.recyclerEventsList.apply {
            this.layoutManager = layoutManager
            this.setHasFixedSize(true)
            this.adapter = eventsAdapter
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        app = context.applicationContext as App
        app.listeners.add(this)
    }

    override fun onDetach() {
        super.onDetach()
        app.listeners.remove(this)
    }

    private fun bind() {
        eventList = app.eventRepository.nearbyEvents
    }

    override fun onEventListUpdated() {
        GlobalScope.launch(Dispatchers.Main) {
            eventsAdapter.notifyDataSetChanged()
        }
    }

}

