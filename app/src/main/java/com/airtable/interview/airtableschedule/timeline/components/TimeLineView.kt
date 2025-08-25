package com.airtable.interview.airtableschedule.timeline.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.airtable.interview.airtableschedule.models.*

@Composable
fun TimeLineView(
    events: List<Event>,
    onEventUpdated: (Event) -> Unit
) {
    if (events.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("There aren't events")
        }
        return
    }

    val minDate = remember(events) { events.minBy { it.startDate }.startDate }
    val maxDate = remember(events) { events.maxBy { it.endDate }.endDate }
    val totalDays = remember(minDate, maxDate) { daysBetweenInclusive(minDate, maxDate) }

    val lanes = remember(events) { assignLanes(events) }

    var dayWidth by remember { mutableStateOf(28.dp) }
    val minDayWidth = 16.dp
    val maxDayWidth = 80.dp

    val baseLaneHeight = 56.dp

    val sharedScroll = rememberScrollState()

    Column(Modifier.fillMaxSize()) {
        ZoomControls(
            dayWidth = dayWidth,
            onZoomIn = { dayWidth = (dayWidth + 4.dp).coerceAtMost(maxDayWidth) },
            onZoomOut = { dayWidth = (dayWidth - 4.dp).coerceAtLeast(minDayWidth) }
        )

        TimeAxis(
            minDate = minDate,
            totalDays = totalDays,
            dayWidth = dayWidth,
            scrollState = sharedScroll
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(lanes) { index, lane ->
                LaneRow(
                    lane = lane,
                    minDate = minDate,
                    dayWidth = dayWidth,
                    totalDays = totalDays,
                    laneHeight = baseLaneHeight,
                    scrollState = sharedScroll,
                    laneIndex = index,
                    onEventUpdated = onEventUpdated
                )
            }
        }
    }
}



