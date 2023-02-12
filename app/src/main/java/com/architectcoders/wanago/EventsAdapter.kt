package com.architectcoders.wanago

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.wanago.databinding.ItemEventBinding
import com.bumptech.glide.Glide

class EventsAdapter(private val events: List <Event>) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(events [position] )
    }

    override fun getItemCount() = events.size

    class ViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.tVArtistName.text = event.name
            binding.tVPlace.text = event.venue
            binding.tVType.text = event.type
            Glide.with(itemView.context).load(event.imageUrl).into(binding.iVPoster)
        }
    }
}

