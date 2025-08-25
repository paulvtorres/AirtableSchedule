package com.airtable.interview.airtableschedule.timeline

import androidx.compose.runtime.*
import androidx.lifecycle.compose.*
import com.airtable.interview.airtableschedule.timeline.components.TimeLineView


@Composable
fun TimelineScreen(
    viewModel: TimelineViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TimeLineView(
        events = uiState.events,
        onEventUpdated = { updatedEvent -> viewModel.updateEvent(updatedEvent) }
    )
}


