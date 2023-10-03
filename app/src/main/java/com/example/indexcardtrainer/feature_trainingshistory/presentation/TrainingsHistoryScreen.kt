package com.example.indexcardtrainer.feature_trainingshistory.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.indexcardtrainer.R
import com.example.indexcardtrainer.core.presentation.navigation.NavigationEvent
import com.example.indexcardtrainer.feature_training.domain.TrainingsLogEntry
import com.example.indexcardtrainer.feature_trainingshistory.presentation.components.TrainingsLogListItem
import com.example.indexcardtrainer.ui.theme.IndexCardTrainerTheme

@Composable
fun TrainingsHistoryScreen(viewModel: TrainingsHistoryViewModel = hiltViewModel()) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        val daysSinceLastTraining = viewModel.calculateDaysSinceLastTraining()
        if (daysSinceLastTraining > 0) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "${stringResource(id = R.string.days_since_last_training)}:",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = daysSinceLastTraining.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (viewModel.loadAllTrainingsLogEntries().isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(viewModel.loadAllTrainingsLogEntries()) { trainingsLogEntry: TrainingsLogEntry ->
                    TrainingsLogListItem(trainingsLogEntry = trainingsLogEntry)
                }
            }
        }
        else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.no_trainings_recorded),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                Button(onClick = { viewModel.onNavigationEvent(NavigationEvent.OnHomeClick) }) {
                    Text(
                        text = stringResource(id = R.string.back),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrainingsHistoryScreenPreview() {
    IndexCardTrainerTheme {
        TrainingsHistoryScreen()
    }
}