package com.airtable.interview.airtableschedule.timeline.components

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.airtable.interview.airtableschedule.models.*
import java.util.*
import kotlin.math.*

@Composable
fun DraggableEventChip(
    event: Event,
    timelineStart: Date,
    dayWidth: Dp,
    prominent: Boolean = false,
    onEventUpdated: (Event) -> Unit,
    heightDp: Dp = 48.dp
) {
    val dayWidthPx = with(LocalDensity.current) { dayWidth.toPx() }
    var accumulatedOffset by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .offset {
                val daysFromStart = offsetDaysFrom(timelineStart, event.startDate)
                IntOffset((daysFromStart * dayWidthPx + accumulatedOffset).roundToInt(), 0)
            }
            .width(maxOf(eventDurationInDays(event) * dayWidth, 32.dp))
            .height(heightDp)
            .pointerInput(event.id) {
                detectDragGestures(
                    onDragEnd = {
                        val daysMoved = (accumulatedOffset / dayWidthPx).roundToInt()
                        if (daysMoved != 0) {
                            val newStart = Date(event.startDate.time + daysMoved * ONE_DAY_MS)
                            val newEnd = Date(event.endDate.time + daysMoved * ONE_DAY_MS)
                            onEventUpdated(event.copy(startDate = newStart, endDate = newEnd))
                        }
                        accumulatedOffset = 0f
                    },
                    onDrag = { change, dragAmount ->
                        change.consumePositionChange()
                        accumulatedOffset += dragAmount.x
                    }
                )
            }
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (prominent) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.secondaryContainer
            )
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = event.name,
                style = MaterialTheme.typography.bodyMedium,
                color = if (prominent) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSecondaryContainer,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${event.startDate.toMonthDay()} → ${event.endDate.toMonthDay()}",
                style = MaterialTheme.typography.labelSmall,
                color = if (prominent)
                    MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f)
                else
                    MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.9f),
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
        }
    }
}