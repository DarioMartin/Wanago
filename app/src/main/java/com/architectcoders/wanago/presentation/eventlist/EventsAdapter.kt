package com.architectcoders.wanago.presentation.eventlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.wanago.databinding.ItemEventBinding
import com.architectcoders.wanago.domain.WanagoEvent
import com.architectcoders.wanago.presentation.common.basicDiffUtil
import com.bumptech.glide.Glide

class EventsAdapter : PagingDataAdapter<WanagoEvent, EventViewHolder>(
    basicDiffUtil(
        { old, new -> old.id == new.id },
        { old, new -> old == new })
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}

class EventViewHolder(private val binding: ItemEventBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(event: WanagoEvent) {
        binding.eventName.text = event.name
        binding.eventVenue.text = event.venue
        Glide.with(itemView.context).load(event.imageUrl).into(binding.eventImage)

        itemView.setOnClickListener { view -> navigateToDetails(view, event) }
    }

    private fun navigateToDetails(view: View, event: WanagoEvent) {
        val action =
            FragmentEventsListDirections.actionFragmentEventsListToEventDetailsFragment(event.id)
        view.findNavController().navigate(action)
    }
}
