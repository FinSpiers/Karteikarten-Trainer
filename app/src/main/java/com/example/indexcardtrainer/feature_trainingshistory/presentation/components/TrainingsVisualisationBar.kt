package com.example.indexcardtrainer.feature_trainingshistory.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.indexcardtrainer.core.domain.model.IndexCard

@Composable
fun TrainingsVisualisationBar(
    cards : List<IndexCard>,
    correctAnsweredCards : List<IndexCard>,
    timeUsedForEachCard : List<Int>,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 4.dp)) {
        for (timeInSec in timeUsedForEachCard) {
            Text(
                text = "$timeInSec s",
                modifier = Modifier.weight(1f),
                style = TextStyle(fontSize = 13.sp),
                textAlign = TextAlign.Center
            )
        }
    }
    Spacer(modifier = Modifier
        .height(8.dp)
        .fillMaxWidth()
        .drawBehind {
            var x = 16.dp
                .toPx()
                .dec()
            val n = (size.width - 32.dp
                .toPx()
                .dec()) / cards.size
            for (card in cards) {
                val path = Path()
                path.moveTo(x, 0f)
                path.fillType = PathFillType.EvenOdd
                path.lineTo(x + n, 0f)
                path.close()
                x += n
                if (correctAnsweredCards.contains(card)) {
                    drawPath(
                        path,
                        Color.Green,
                        style = Stroke(width = 5.dp.toPx())
                    )
                } else {
                    drawPath(
                        path,
                        Color.Red,
                        style = Stroke(width = 5.dp.toPx())
                    )
                }
            }
        }
    )
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
        for (card in cards) {
            Text(
                text = card.title,
                modifier = Modifier.weight(1f),
                style = TextStyle(fontSize = 13.sp),
                textAlign = TextAlign.Center
            )
        }
    }
}