package com.example.indexcardtrainer.feature_training.presentation

import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.indexcardtrainer.R
import com.example.indexcardtrainer.core.presentation.navigation.NavigationEvent
import com.example.indexcardtrainer.feature_training.presentation.components.TrainingsInfoBox
import com.example.indexcardtrainer.feature_training.presentation.components.TrainingsMotivationSection
import com.example.indexcardtrainer.ui.theme.IndexCardTrainerTheme

@Composable
fun TrainingsScreen(viewModel: TrainingsViewModel = hiltViewModel()) {
    val trainingsState = viewModel.trainingsStateFlow.collectAsState().value
    /******************************* Dialog(s) *******************************/
    if (viewModel.shouldShowTrainingFinishedDialog.value) {
        Dialog(
            onDismissRequest = {
                viewModel.onNavigationEvent(NavigationEvent.OnHomeClick)
                viewModel.shouldShowTrainingFinishedDialog.value = false
                viewModel.calculateUserRank(viewModel.loadUserData().rubberDots + trainingsState.rubberDotsEarned)
            }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.shapes.large
                    )
                    .padding(32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image_trophy),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
                        .fillMaxWidth(0.4f)

                )
                Text(
                    text = stringResource(id = R.string.trainings_result),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                val progress = animateFloatAsState(
                    targetValue = trainingsState.correctAnsweredCards.size.toFloat() / trainingsState.cards.size.toFloat(),
                    animationSpec = FloatTweenSpec(3000, 1000)
                )
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        progress = progress.value,
                        strokeWidth = 15.dp,
                        color = Color.Green,
                        trackColor = Color.Red,
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .aspectRatio(1f)
                            .padding(16.dp)
                    )
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                           Box(modifier = Modifier
                               .size(24.dp)
                               .clip(CircleShape)
                               .background(Color.Green)
                           )
                           Text(
                               text = stringResource(id = R.string.correct_answers),
                               modifier = Modifier.padding(start = 4.dp)
                           )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color.Red)
                            )
                            Text(
                                text = stringResource(id = R.string.wrong_answers),
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }

                Text(text = "${stringResource(id = R.string.rubber_dots_earned)}: ${trainingsState.rubberDotsEarned}")
                Button(onClick = {
                    viewModel.onNavigationEvent(NavigationEvent.OnHomeClick)
                    viewModel.shouldShowTrainingFinishedDialog.value = false
                    viewModel.calculateUserRank(viewModel.user.rubberDots + trainingsState.rubberDotsEarned)

                }) {
                    Text(text = stringResource(id = R.string._continue))
                }
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        TrainingsInfoBox(
            currentCardNr = trainingsState.cards.indexOf(trainingsState.currentIndexCard) + 1,
            numberOfCards = trainingsState.cards.size,
            rubberDotsEarned = trainingsState.rubberDotsEarned,
            currentMultiplicator = trainingsState.currentMultiplicator
        )
        Text(
            text = stringResource(id = R.string.translate_the_following_word),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 32.dp),
            textAlign = TextAlign.Center
        )
        TrainingsMotivationSection(
            trainingsState = trainingsState,
            onCorrectAnswered = { viewModel.onTrainingEvent(TrainingsEvent.CorrectAnswered) },
            onWrongAnswered = { viewModel.onTrainingEvent(TrainingsEvent.WrongAnswered) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TrainingsScreenPreview() {
    IndexCardTrainerTheme {
        TrainingsScreen()
    }
}