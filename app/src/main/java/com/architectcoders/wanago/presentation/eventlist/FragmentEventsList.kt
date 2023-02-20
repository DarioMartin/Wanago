package com.architectcoders.wanago.presentation.eventlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.architectcoders.wanago.databinding.FragmentEventsListBinding
import com.architectcoders.wanago.presentation.common.launchAndCollect
import com.architectcoders.wanago.presentation.common.setVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentEventsList : Fragment() {

    private var _binding: FragmentEventsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EventListViewModel by viewModels()

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

        binding.swiper.setOnRefreshListener { viewModel.getEvents() }

        viewLifecycleOwner.launchAndCollect(viewModel.state) { state ->
            updateUiState(state)
        }

        eventsAdapter = EventsAdapter {
            viewModel.switchFavorite(it)
        }

        binding.recyclerEventsList.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
            this.adapter = eventsAdapter
        }
    }

    private fun updateUiState(state: UiState) {
        binding.swiper.isRefreshing = false

        binding.progress.setVisible(state.loading)

        viewLifecycleOwner.lifecycleScope.launch {
            state.events?.let { eventsAdapter.submitData(it) }
        }
    }

}

