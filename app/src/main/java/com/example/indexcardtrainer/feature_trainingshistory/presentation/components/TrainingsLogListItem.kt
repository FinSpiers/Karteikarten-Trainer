package com.example.indexcardtrainer.feature_trainingshistory.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.indexcardtrainer.core.domain.model.IndexCard
import com.example.indexcardtrainer.core.domain.util.DateTimeConverter
import com.example.indexcardtrainer.feature_training.domain.TrainingsLogEntry
import com.example.indexcardtrainer.ui.theme.IndexCardTrainerTheme
import java.time.Instant
import java.time.format.DateTimeFormatter

@Composable
fun TrainingsLogListItem(
    trainingsLogEntry: TrainingsLogEntry
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                Text(
                    text = DateTimeConverter.parseDateTime(trainingsLogEntry.timestamp).format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")),
                    modifier = Modifier.padding(start = 10.dp, top = 4.dp),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                        Text(text = "Cards trained:")
                        Text(text = "Correct answered:")
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "${trainingsLogEntry.cards.size}")
                        Text(text = "${trainingsLogEntry.correctAnsweredCards.size}")
                    }
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                val percentage = if(trainingsLogEntry.cards.isNotEmpty()) trainingsLogEntry.correctAnsweredCards.size.toFloat() / trainingsLogEntry.cards.size.toFloat()
                                 else 0.toFloat()
                TrainingsFeedbackThumbBox(percentage = percentage)
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth(0.45f)) {
                Text(text = "Total time trained:")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = DateTimeConverter.formatRuntime(trainingsLogEntry.duration),
                    textAlign = TextAlign.Center

                )
            }
        }
        TrainingsVisualisationBar(
            cards = trainingsLogEntry.cards,
            correctAnsweredCards = trainingsLogEntry.correctAnsweredCards,
            timeUsedForEachCard = trainingsLogEntry.timeUsedForEachCard
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 16.dp)) {
            Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                Text(
                    text = "Rubber dots earned:",
                    fontWeight = FontWeight.Bold
                )
            }
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${trainingsLogEntry.rubberDotsEarned}",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrainingsLogListItemPreview() {
    val cards = listOf(
        IndexCard(
            id = 1,
            title = "TestItem1efeadawdad",
            solution = "testitem1",
            category = null,
            totalAttempts = 7,
            timesCorrectAnswered = 4,
            isRecentlyFailed = true,
            isOftenFailed = false
        ),
        IndexCard(
            id = 2,
            title = "TestItem2",
            solution = "testitem2",
            category = null,
            totalAttempts = 3,
            timesCorrectAnswered = 3,
            isRecentlyFailed = false,
            isOftenFailed = false
        ),
        IndexCard(
            id = 3,
            title = "TestItem3",
            solution = "testitem3",
            category = null,
            totalAttempts = 3,
            timesCorrectAnswered = 3,
            isRecentlyFailed = false,
            isOftenFailed = false
        ),
        IndexCard(
            id = 4,
            title = "TestItem4",
            solution = "testitem4",
            category = null,
            totalAttempts = 3,
            timesCorrectAnswered = 3,
            isRecentlyFailed = false,
            isOftenFailed = false
        ),
        IndexCard(
            id = 5,
            title = "TestItem5",
            solution = "testitem5",
            category = null,
            totalAttempts = 3,
            timesCorrectAnswered = 3,
            isRecentlyFailed = false,
            isOftenFailed = false
        ),
        IndexCard(
            id = 6,
            title = "TestItem6",
            solution = "testitem6",
            category = null,
            totalAttempts = 3,
            timesCorrectAnswered = 3,
            isRecentlyFailed = false,
            isOftenFailed = false
        )
    )
    IndexCardTrainerTheme {
        TrainingsLogListItem(trainingsLogEntry = TrainingsLogEntry(
            timestamp = Instant.now().epochSecond,
            cards = cards,
            correctAnsweredCards = listOf(cards[1], cards[2], cards[4], cards[5]),
            duration = 3665,
            timeUsedForEachCard = listOf(6, 4, 6, 4, 10, 5),
            rubberDotsEarned = 20
        ))
    }
}