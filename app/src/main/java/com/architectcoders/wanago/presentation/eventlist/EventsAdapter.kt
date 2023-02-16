package com.architectcoders.wanago.presentation.eventlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.wanago.databinding.ItemEventBinding
import com.architectcoders.wanago.domain.model.Event
import com.bumptech.glide.Glide

class EventsAdapter(private var events: List<Event> = listOf()) :
    RecyclerView.Adapter<EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

    override fun getItemCount() = events.size
    fun setEvents(events: List<Event>) {
        this.events = events
        notifyDataSetChanged()
    }

}

class EventViewHolder(private val binding: ItemEventBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(event: Event) {
        binding.eventName.text = event.name
        binding.eventVenue.text = event.venue
        Glide.with(itemView.context).load(event.imageUrl).into(binding.eventImage)

        itemView.setOnClickListener { view -> navigateToDetails(view, event) }
    }

    private fun navigateToDetails(view: View, event: Event) {
        val action =
            FragmentEventsListDirections.actionFragmentEventsListToEventDetailsFragment(event.id)
        view.findNavController().navigate(action)
    }
}