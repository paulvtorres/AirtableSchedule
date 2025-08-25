package com.airtable.interview.airtableschedule.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airtable.interview.airtableschedule.models.*
import com.airtable.interview.airtableschedule.repositories.EventDataRepository
import com.airtable.interview.airtableschedule.repositories.EventDataRepositoryImpl
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * ViewModel responsible for managing the state of the timeline screen.
 */

class TimelineViewModel : ViewModel() {
    private val eventDataRepository: EventDataRepository = EventDataRepositoryImpl()

    private val _uiState = MutableStateFlow(TimelineUiState())
    val uiState: StateFlow<TimelineUiState> = _uiState

    init {
        viewModelScope.launch {
            eventDataRepository.getTimelineItems().collect { events ->
                _uiState.value = TimelineUiState(events)
            }
        }
    }

    fun updateEvent(updatedEvent: Event) {
        _uiState.update { currentState ->
            val newList = currentState.events.map {
                if (it.id == updatedEvent.id) updatedEvent else it
            }
            currentState.copy(events = newList)
        }
    }
}

