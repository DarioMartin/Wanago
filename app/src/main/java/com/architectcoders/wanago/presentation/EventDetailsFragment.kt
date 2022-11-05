package com.architectcoders.wanago.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.architectcoders.wanago.data.repository.EventRepository
import com.architectcoders.wanago.databinding.FragmentEventDetailsBinding
import com.bumptech.glide.Glide

class EventDetailsFragment : Fragment() {

    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var eventId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            eventId = EventDetailsFragmentArgs.fromBundle(it).eventId
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        EventRepository.getEventById(eventId)?.let { event ->
            binding.artistName.text = event.artistName
            binding.eventPlace.text = event.place
            Glide.with(requireContext()).load(event.poster).into(binding.eventImage)
        }

    }

}