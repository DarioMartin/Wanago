package com.architectcoders.wanago.presentation.eventdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.architectcoders.wanago.databinding.FragmentEventDetailsBinding
import com.architectcoders.wanago.domain.WanagoEvent
import com.architectcoders.wanago.presentation.common.launchAndCollect
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventDetailsFragment : Fragment() {

    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EventDetailsViewModel by viewModels()

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

        viewLifecycleOwner.launchAndCollect(viewModel.event) { event ->
            event?.let { paintEventDetails(it) }
        }

        viewModel.loadEvent(eventId)

    }

    private fun paintEventDetails(event: WanagoEvent) {
        binding.detailsEventName.text = event.name
        binding.detailsEventVenue.text = event.venue
        Glide.with(requireContext()).load(event.imageUrl).into(binding.eventImage)
    }

}
