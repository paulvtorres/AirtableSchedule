package com.airtable.interview.airtableschedule.timeline.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*
import com.airtable.interview.airtableschedule.models.*
import java.util.*

@Composable
fun LaneRow(
    lane: List<Event>,
    minDate: Date,
    dayWidth: Dp,
    totalDays: Int,
    laneHeight: Dp,
    scrollState: ScrollState,
    laneIndex: Int,
    onEventUpdated: (Event) -> Unit
) {
    val totalWidth = totalDays * dayWidth
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(laneHeight)
            .horizontalScroll(scrollState)
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (laneIndex % 2 == 0) MaterialTheme.colorScheme.surfaceVariant
                else MaterialTheme.colorScheme.surface
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.width(totalWidth)) {
            lane.forEachIndexed { idx, event ->
                DraggableEventChip(
                    event = event,
                    timelineStart = minDate,
                    dayWidth = dayWidth,
                    prominent = idx % 3 == 0,
                    onEventUpdated = onEventUpdated,
                    heightDp = laneHeight - 8.dp
                )
            }
        }
    }
}

