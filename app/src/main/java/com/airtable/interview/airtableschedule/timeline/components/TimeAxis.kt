package com.airtable.interview.airtableschedule.timeline.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.airtable.interview.airtableschedule.models.*
import java.util.*

@Composable
fun TimeAxis(
    minDate: Date,
    totalDays: Int,
    dayWidth: Dp,
    scrollState: ScrollState
) {
    val tickEveryDays = when {
        dayWidth >= 48.dp -> 1
        dayWidth >= 24.dp -> 2
        dayWidth >= 16.dp -> 4
        dayWidth >= 8.dp -> 7
        else -> 14
    }

    val totalWidth = totalDays * dayWidth
    val axisHeight = if (dayWidth < 16.dp) 24.dp else 36.dp
    val textStyle = if (dayWidth < 16.dp) MaterialTheme.typography.labelSmall else MaterialTheme.typography.labelMedium

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(axisHeight)
            .horizontalScroll(scrollState)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .width(totalWidth + dayWidth)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            repeat(totalDays) { d ->
                Box(
                    modifier = Modifier
                        .width(dayWidth)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    if (d % tickEveryDays == 0) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Divider(
                                thickness = 2.dp,
                                color = MaterialTheme.colorScheme.outline,
                                modifier = Modifier.height(16.dp)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = dayLabel(minDate, d),
                                style = textStyle,
                                maxLines = 1,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            val lastDay = totalDays - 1
            Box(
                modifier = Modifier
                    .width(dayWidth)
                    .fillMaxHeight(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Divider(
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.height(16.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = dayLabel(minDate, lastDay),
                        style = textStyle,
                        maxLines = 1,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

