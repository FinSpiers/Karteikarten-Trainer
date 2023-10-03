package com.example.indexcardtrainer.feature_trainingshistory.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.indexcardtrainer.R
import com.example.indexcardtrainer.ui.theme.DarkGreen
import com.example.indexcardtrainer.ui.theme.IndexCardTrainerTheme
import com.example.indexcardtrainer.ui.theme.Orange30

@Composable
fun TrainingsFeedbackThumbBox(percentage : Float) {
    val imageId = when(percentage) {
        in 0.0f..0.25f -> R.drawable.thumb_down
        in 0.26f..0.66f -> R.drawable.thumb_mid
        else -> R.drawable.thumb_up
    }
    val tint = when(percentage) {
        in 0.0f..0.25f -> Color.Red
        in 0.26f..0.66f -> Orange30
        else -> DarkGreen
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "trainings feedback",
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            colorFilter = ColorFilter.tint(tint)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TrainingsFeedbackThumbBoxPreview() {
    IndexCardTrainerTheme {
        TrainingsFeedbackThumbBox(percentage = 0.26f)
    }
}