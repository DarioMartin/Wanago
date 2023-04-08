package com.architectcoders.wanago.presentation.eventlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.wanago.R
import com.architectcoders.wanago.databinding.ItemEventBinding
import com.architectcoders.wanago.domain.WanagoEvent
import com.architectcoders.wanago.presentation.common.basicDiffUtil
import com.bumptech.glide.Glide

class EventsAdapter(private val favListener: (WanagoEvent) -> Unit) :
    PagingDataAdapter<WanagoEvent, EventViewHolder>(
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
        getItem(position)?.let { holder.bind(it, favListener) }
    }

}

class EventViewHolder(private val binding: ItemEventBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(event: WanagoEvent, favListener: (WanagoEvent) -> Unit) {
        binding.eventName.text = event.name
        binding.eventVenue.text = event.venue
        Glide.with(itemView.context)
            .load(event.imageUrl)
            .placeholder(R.mipmap.ic_launcher_foreground)
            .error(R.mipmap.ic_launcher_foreground)
            .into(binding.eventImage)

        val favRes = if (event.isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
        binding.favoriteButton.apply {
            setImageDrawable(ContextCompat.getDrawable(itemView.context, favRes))
            setOnClickListener { favListener(event) }
        }

        itemView.setOnClickListener { view -> navigateToDetails(view, event) }
    }

    private fun navigateToDetails(view: View, event: WanagoEvent) {
        val action =
            FragmentEventsListDirections.actionFragmentEventsListToEventDetailsFragment(event.id)
        view.findNavController().navigate(action)
    }
}
