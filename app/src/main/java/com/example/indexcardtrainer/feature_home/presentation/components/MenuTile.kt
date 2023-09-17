package com.example.indexcardtrainer.feature_home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MenuTile(
    text: String,
    imageVector: ImageVector,
    backgroundResId: Int,
    modifier: Modifier = Modifier,
    hasIcon : Boolean = true,
    isIconLeft: Boolean = true,
    onClick: () -> Unit = {},
    isEnabled : Boolean = true
) {
    val outlineColor : Color
    val colorFilter : ColorFilter?
    if (isEnabled) {
        outlineColor = MaterialTheme.colorScheme.outline
        colorFilter = null
    }
    else {
        outlineColor = Color.DarkGray
        colorFilter = ColorFilter.tint(Color.LightGray, BlendMode.Hue)
    }
    Box(
        modifier = modifier
            .border(4.dp, outlineColor, MaterialTheme.shapes.large)
            .clickable {
                if (isEnabled) {
                    onClick()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = backgroundResId),
            contentDescription = null,
            modifier = Modifier.fillMaxSize().clip(MaterialTheme.shapes.large),
            contentScale = ContentScale.Crop,
            colorFilter = colorFilter
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (hasIcon && isIconLeft) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.3f)
                        .padding(start = 4.dp),
                    tint = Color.Black
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )
            if (hasIcon && !isIconLeft) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.3f)
                        .padding(end = 4.dp),
                    tint = Color.Black
                )
            }
        }
    }
}