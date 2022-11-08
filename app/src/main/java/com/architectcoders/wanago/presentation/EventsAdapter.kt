package com.architectcoders.wanago.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.wanago.databinding.ItemEventBinding
import com.architectcoders.wanago.domain.model.Event
import com.bumptech.glide.Glide

class EventsAdapter(private val events: List<Event>) :
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

}

class EventViewHolder(private val binding: ItemEventBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(event: Event) {
        binding.tVArtistName.text = event.artistName
        binding.tVPlace.text = event.place
        Glide.with(itemView.context).load(event.poster).into(binding.iVPoster)

        itemView.setOnClickListener { view -> navigateToDetails(view, event) }
    }

    private fun navigateToDetails(view: View, event: Event) {
        val action =
            FragmentEventsListDirections.actionFragmentEventsListToEventDetailsFragment(event.id)
        view.findNavController().navigate(action)
    }
}