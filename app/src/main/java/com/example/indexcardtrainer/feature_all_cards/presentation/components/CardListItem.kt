package com.example.indexcardtrainer.feature_all_cards.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.indexcardtrainer.core.domain.model.IndexCard
data class DropDownItem(
    val text : String
)

@Composable
fun CardListItem(
    card: IndexCard,
    dropDownItems : List<DropDownItem>,
    modifier: Modifier = Modifier,
    onClick : (DropDownItem) -> Unit
) {
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current
    Row(
        modifier = modifier
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }
            .fillMaxWidth()
            .padding(2.dp)
            .background(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.shapes.medium)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${card.title} - ${card.solution}",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
        Column(horizontalAlignment = Alignment.End, ) {
            if (card.category != null) {
                Text(text = card.category!!, modifier = Modifier.padding(bottom = 4.dp, end = 8.dp))
            }
            Icon(
                imageVector = Icons.Rounded.MoreVert, contentDescription = "show details",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        isContextMenuVisible = true
                    }
            )
            DropdownMenu(
                expanded = isContextMenuVisible,
                onDismissRequest = { isContextMenuVisible = false }) {
                dropDownItems.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text=item.text) },
                        onClick = {
                            onClick(item)
                            isContextMenuVisible = false
                        }
                    )
                }
            }
        }
    }
}