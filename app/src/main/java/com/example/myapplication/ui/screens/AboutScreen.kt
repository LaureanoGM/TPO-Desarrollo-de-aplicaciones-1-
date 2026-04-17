package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

data class ParticipantUi(
    val name: String,
    val initials: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onBack: () -> Unit) {
    val participants = listOf(
        ParticipantUi("Nombre Compañero 1", "C1"),
        ParticipantUi("Nombre Compañero 2", "C2")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Acerca de") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Desarrollo de Aplicaciones I - TPO",
                style = MaterialTheme.typography.titleMedium
            )

            participants.forEach { participant ->
                ParticipantPlaceholderItem(participant)
            }
        }
    }
}

@Composable
fun ParticipantPlaceholderItem(participant: ParticipantUi) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape),
            shape = CircleShape,
            tonalElevation = 4.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = participant.initials,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = participant.name,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}