package com.airtable.interview.airtableschedule.timeline.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*

@Composable
fun ZoomControls(
    dayWidth: Dp,
    onZoomIn: () -> Unit,
    onZoomOut: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Zoom", style = MaterialTheme.typography.labelLarge)
        OutlinedButton(onClick = onZoomOut) { Text("–") }
        OutlinedButton(onClick = onZoomIn) { Text("+") }
        Text(text = "día ≈ ${dayWidth.value.toInt()}dp", style = MaterialTheme.typography.labelMedium)
    }
}