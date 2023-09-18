package com.example.indexcardtrainer.feature_home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.indexcardtrainer.R

@Composable
fun UserInfoBar(userRank: String, userRubberDots: Int, imageResId : Int?) {
    val infoSectionToggled = remember { mutableStateOf(false) }
    val infoIconTint = if (infoSectionToggled.value) MaterialTheme.colorScheme.outlineVariant else MaterialTheme.colorScheme.onTertiaryContainer
    Column(modifier = Modifier.padding(2.dp).background(
        MaterialTheme.colorScheme.tertiaryContainer, MaterialTheme.shapes.medium
    )) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(id = R.string.your_rank))
                Text(
                    text = userRank,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 8.dp, end = 4.dp),
                    fontWeight = FontWeight.Bold
                )
                if (userRank != stringResource(id = R.string.rank_recruit) && imageResId != null) {
                    Image(painter = painterResource(id = imageResId), contentDescription = null, modifier = Modifier
                        .clip(CircleShape)
                        .size(36.dp))
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .size(26.dp)
                    .padding(4.dp))
                Text(
                    text = userRubberDots.toString(),
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
                Icon(imageVector = Icons.Rounded.Info, contentDescription = "show info", modifier = Modifier
                    .size(32.dp)
                    .padding(end = 4.dp)
                    .clickable { infoSectionToggled.value = !infoSectionToggled.value },
                    tint = infoIconTint
                )
            }
        }
        if (infoSectionToggled.value) {
            Row(modifier = Modifier.fillMaxWidth().padding(start = 64.dp, end = 8.dp, bottom = 8.dp), horizontalArrangement = Arrangement.End) {
                Box(
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant, MaterialTheme.shapes.small)
                        //.background(MaterialTheme.colorScheme.outlineVariant, MaterialTheme.shapes.small)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Get rubber dots by training your cards! Get even more if you consecutively answer correct!",
                        color = MaterialTheme.colorScheme.onTertiary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}