package com.architectcoders.wanago.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.wanago.databinding.ItemEventBinding
import com.architectcoders.wanago.domain.model.Event
import com.bumptech.glide.Glide

class EventsAdapter(
    private val events: List<Event>,
    private val onEventSelected: (Event) -> Unit
) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        holder.apply {
            bind(event)
            itemView.setOnClickListener { onEventSelected(event) }
        }
    }

    override fun getItemCount() = events.size

    class ViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.tVArtistName.text = event.artistName
            binding.tVPlace.text = event.place
            Glide.with(itemView.context).load(event.poster).into(binding.iVPoster)
        }
    }
}