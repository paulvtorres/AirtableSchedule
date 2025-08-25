package com.airtable.interview.airtableschedule.models

import java.util.*

fun assignLanes(events: List<Event>): List<List<Event>> {
    val lanes = mutableListOf<MutableList<Event>>()
    events.sortedBy { it.startDate }.forEach { event ->
        val lane = lanes.firstOrNull { l ->
            l.none { it.endDate >= event.startDate && it.startDate <= event.endDate }
        }
        if (lane != null) lane.add(event) else lanes.add(mutableListOf(event))
    }
    return lanes
}


const val ONE_DAY_MS = 86_400_000L

fun daysBetweenInclusive(start: Date, end: Date): Int {
    val diff = (end.time - start.time) / ONE_DAY_MS
    return maxOf(1, diff.toInt() + 1)
}

fun eventDurationInDays(event: Event): Int =
    daysBetweenInclusive(event.startDate, event.endDate)

fun offsetDaysFrom(start: Date, target: Date): Int {
    val diff = (target.time - start.time) / ONE_DAY_MS
    return diff.toInt()
}

fun Date.toMonthDay(): String {
    val cal = Calendar.getInstance().apply { time = this@toMonthDay }
    return "${cal.get(Calendar.MONTH) }/${cal.get(Calendar.DAY_OF_MONTH)}"
}

fun dayLabel(start: Date, offsetDays: Int): String {
    val ms = 86_400_000L * offsetDays
    val d = Date(start.time + ms)
    @Suppress("DEPRECATION")
    return "${d.month }/${d.date}"
}

