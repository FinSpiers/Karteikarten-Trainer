package com.example.indexcardtrainer.feature_all_cards.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.indexcardtrainer.R
import com.example.indexcardtrainer.core.domain.model.IndexCard
import com.example.indexcardtrainer.core.domain.util.DateTimeConverter

@Composable
fun CardDetailsDialog(
    shouldShowCardDetailsDialog: MutableState<Boolean>,
    getSelectedCard: () -> IndexCard?,
    resetSelectedIndexCard: () -> Unit
) {
    if (shouldShowCardDetailsDialog.value) {
        val selectedCard = getSelectedCard()
        Dialog(
            onDismissRequest = {
                shouldShowCardDetailsDialog.value = false
                resetSelectedIndexCard()
            }
        ) {
            if (selectedCard != null) {
                val dateTime = DateTimeConverter.parseDateTime(selectedCard.timeStamp)
                Column(
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.large).padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(id = R.string.details), style = MaterialTheme.typography.displaySmall)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = stringResource(id = R.string.creation) + ": ${dateTime.dayOfMonth}.${dateTime.monthValue}.${dateTime.year}, ${dateTime.hour}:${dateTime.minute}")

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = stringResource(id = R.string.total_attempts) + ": ${selectedCard.totalAttempts}")

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = stringResource(id = R.string.times_correct_answered) + ": ${selectedCard.timesCorrectAnswered}")

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = stringResource(id = R.string.correct_answered_streak) + ": ${selectedCard.correctAnsweredStreak}")

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}