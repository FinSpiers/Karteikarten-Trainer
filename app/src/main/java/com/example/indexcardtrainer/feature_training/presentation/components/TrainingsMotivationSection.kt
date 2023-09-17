package com.example.indexcardtrainer.feature_training.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.indexcardtrainer.R
import com.example.indexcardtrainer.feature_training.presentation.TrainingsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingsMotivationSection(
    trainingsState: TrainingsState,
    onCorrectAnswered: () -> Unit,
    onWrongAnswered: () -> Unit
) {
    val textFieldState = remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = trainingsState.currentIndexCard.title,
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(32.dp))
        TextField(
            value = textFieldState.value,
            onValueChange = { textFieldState.value = it },
            textStyle = MaterialTheme.typography.titleLarge,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.translation),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            trailingIcon = {
                if (textFieldState.value.isNotBlank()) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = "clear",
                        modifier = Modifier.clickable {
                            textFieldState.value = ""
                        })
                }
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        if (trainingsState.motivationText != null) {
            Text(
                text = trainingsState.motivationText,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))

        }
        if (trainingsState.currentCardCorrectAnswered) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = null,
                tint = Color.Green,
                modifier = Modifier.size(50.dp)
            )
            textFieldState.value = ""
        } else if (trainingsState.currentCardWrongAnswered) {
            Icon(
                imageVector = Icons.Rounded.Clear,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(50.dp)
            )
            textFieldState.value = ""
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (textFieldState.value == trainingsState.currentIndexCard.solution) {
                    onCorrectAnswered()
                } else {
                    onWrongAnswered()
                }
            },
            shape = MaterialTheme.shapes.medium
            ) {
            Text(
                text = stringResource(id = R.string.check),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(8.dp)
            )
        }

    }
}