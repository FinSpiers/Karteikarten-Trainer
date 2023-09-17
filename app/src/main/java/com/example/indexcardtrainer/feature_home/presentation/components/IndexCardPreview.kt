package com.example.indexcardtrainer.feature_home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontFamily
import com.example.indexcardtrainer.core.domain.model.IndexCard

@Composable
fun IndexCardPreview(indexCard: IndexCard) {
    val text = "${indexCard.title} - ${indexCard.solution}"
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        if (indexCard.category != null) {
            Row(horizontalArrangement = Arrangement.End) {
                Text(text = indexCard.category!!)
            }
        }
        Text(
            text = text,
            style = MaterialTheme.typography.displaySmall,
            fontFamily = FontFamily.Serif
        )
    }
}