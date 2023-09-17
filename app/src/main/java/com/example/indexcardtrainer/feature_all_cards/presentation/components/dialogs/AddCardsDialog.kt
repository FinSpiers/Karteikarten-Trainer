package com.example.indexcardtrainer.feature_all_cards.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.indexcardtrainer.R
import com.example.indexcardtrainer.core.presentation.IndexCardState
import com.example.indexcardtrainer.feature_all_cards.presentation.CardEvent

@Composable
fun AddCardsDialog(shouldShow : MutableState<Boolean>, onCardEvent: (CardEvent) -> Unit) {
    val isFullyExpanded = remember { mutableStateOf(false) }
    val isPartiallyExpanded = remember { mutableStateOf(false) }
    val cardStateList : MutableList<MutableState<IndexCardState>> = remember { mutableListOf() }
    val modifier = if (isFullyExpanded.value) Modifier.fillMaxSize() else Modifier.fillMaxWidth()
    val headerText = if (isFullyExpanded.value) stringResource(id = R.string.add_cards) else stringResource(id = R.string.add_card)
    val buttonText = if (isFullyExpanded.value) stringResource(id = R.string.save_cards) else stringResource(id = R.string.save_card)
    val numberOfAddEditCardRow = if (isFullyExpanded.value) 10 else if (isPartiallyExpanded.value) 5 else 1
    if (shouldShow.value) {
        Dialog(
            onDismissRequest = {
                shouldShow.value = false
                isFullyExpanded.value = false
                isPartiallyExpanded.value = false
                               },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.large)
            ) {
                Text(
                    text = headerText,
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn() {
                    for (i in 0 until numberOfAddEditCardRow) {
                        val indexCardState = mutableStateOf(IndexCardState())
                        cardStateList.add(i, indexCardState)
                        item { AddEditCardRow(state = indexCardState) }
                    }

                }
                Spacer(modifier = Modifier.height(8.dp))
                if (!isFullyExpanded.value) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.AddCircle,
                            contentDescription = stringResource(id = R.string.add_more),
                            modifier = Modifier
                                .size(32.dp)
                                .clickable {
                                    if (!isPartiallyExpanded.value) {
                                        isPartiallyExpanded.value = true
                                    }
                                    else {
                                        isFullyExpanded.value = true
                                    }
                                }
                        )
                        Text(text = stringResource(id = R.string.add_more))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    cardStateList.forEach {
                        if (it.value.text.isNotEmpty() && it.value.translation.isNotBlank()) {
                            onCardEvent(
                                CardEvent.CardCreation(
                                    it.value.text,
                                    it.value.translation,
                                    it.value.category
                                )
                            )
                            it.value.text = ""
                            it.value.translation = ""
                            it.value.category = null
                        }
                    }
                    shouldShow.value = false
                    isPartiallyExpanded.value = false
                    isFullyExpanded.value = false
                }) {
                    Text(
                        text = buttonText,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

