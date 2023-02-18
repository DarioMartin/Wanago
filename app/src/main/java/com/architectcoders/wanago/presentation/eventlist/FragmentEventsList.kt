package com.architectcoders.wanago.presentation.eventlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.architectcoders.wanago.BuildConfig
import com.architectcoders.wanago.data.AndroidPermissionChecker
import com.architectcoders.wanago.data.EventsRepository
import com.architectcoders.wanago.data.PlayServicesLocationDataSource
import com.architectcoders.wanago.data.RegionRepository
import com.architectcoders.wanago.data.database.EventRoomDataSource
import com.architectcoders.wanago.data.server.TicketMasterDataSource
import com.architectcoders.wanago.databinding.FragmentEventsListBinding
import com.architectcoders.wanago.presentation.common.app
import com.architectcoders.wanago.presentation.common.launchAndCollect
import com.architectcoders.wanago.presentation.common.setVisible

class FragmentEventsList : Fragment() {

    private var _binding: FragmentEventsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EventListViewModel by viewModels {
        val application = requireActivity().app
        EventListViewModelFactory(
            EventsRepository(
                RegionRepository(
                    PlayServicesLocationDataSource(application),
                    AndroidPermissionChecker(application)
                ),
                EventRoomDataSource(requireActivity().app.db.eventDao()),
                TicketMasterDataSource(BuildConfig.ticketMasterApiKey)
            )
        )
    }

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

        binding.swiper.setOnRefreshListener { viewModel.getEvents(forceUpdate = true) }

        viewLifecycleOwner.launchAndCollect(viewModel.state) { state ->
            updateUiState(state)
        }

        eventsAdapter = EventsAdapter()

        binding.recyclerEventsList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
            this.adapter = eventsAdapter
        }
    }

    private fun updateUiState(state: UiState) {
        binding.swiper.isRefreshing = false

        binding.progress.setVisible(state.loading)

        if (state.events?.isEmpty() == false) {
            eventsAdapter.setEvents(state.events)
        }

        binding.emptyListMessage.setVisible(state.events?.isEmpty() == true)

    }

}

