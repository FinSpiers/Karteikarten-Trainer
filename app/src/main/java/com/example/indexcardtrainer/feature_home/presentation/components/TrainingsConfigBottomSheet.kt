@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.indexcardtrainer.feature_home.presentation.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.indexcardtrainer.R
import com.example.indexcardtrainer.core.domain.model.IndexCard
import com.example.indexcardtrainer.feature_home.presentation.states.CardSelectionState
import com.example.indexcardtrainer.feature_home.presentation.states.CategorySelectionState
import com.example.indexcardtrainer.feature_home.presentation.HomeEvent

import kotlinx.coroutines.launch
import kotlin.reflect.KFunction0

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TrainingsConfigBottomSheet(
    shouldShowBottomSheet: MutableState<Boolean>,
    loadAllCards: KFunction0<List<IndexCard>>,
    onHomeEvent: (HomeEvent) -> Unit,
    cardSelectionStateList: List<CardSelectionState>,
    categorySelectionStateList: List<CategorySelectionState>,
    refresh: () -> Unit,
) {
    LaunchedEffect(true) {
        refresh()
    }
    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
        confirmValueChange = { true }
    )
    if (shouldShowBottomSheet.value && loadAllCards().isNotEmpty()) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    modalBottomSheetState.hide()
                    shouldShowBottomSheet.value = false
                }
            },
            shape = MaterialTheme.shapes.large,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.trainings_config),
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = R.string.trainings_config_text),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text= stringResource(id = R.string.categories), style = MaterialTheme.typography.bodyMedium, fontStyle = FontStyle.Italic, textAlign = TextAlign.Start, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp))
                FlowRow(modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outlineVariant,
                        MaterialTheme.shapes.large
                    )
                    .background(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.shapes.large
                    )
                    .padding(horizontal = 4.dp)
                ) {
                    categorySelectionStateList.forEach { categoryState ->
                        if (categoryState.category != null) {
                            ElevatedFilterChip(
                                selected = categoryState.isSelected.value,
                                label = { Text(text = categoryState.category) },
                                onClick = {
                                    categoryState.isSelected.value = !categoryState.isSelected.value
                                    if (categoryState.isSelected.value) {
                                        onHomeEvent(HomeEvent.CategorySelected(categoryState.category))
                                    }
                                    else {
                                        onHomeEvent(HomeEvent.CategoryDeselected(categoryState.category))
                                    }
                                },
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (modalBottomSheetState.currentValue.name == "Expanded") {
                    Text(text= stringResource(id = R.string.cards), style = MaterialTheme.typography.bodyMedium, fontStyle = FontStyle.Italic, textAlign = TextAlign.Start, modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.8f)
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.outlineVariant,
                                MaterialTheme.shapes.large
                            )
                            .background(
                                MaterialTheme.colorScheme.surface,
                                MaterialTheme.shapes.large
                            )
                    ) {
                        cardSelectionStateList.forEach { cardState ->
                            item {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Checkbox(
                                            checked = cardState.isSelected.value,
                                            onCheckedChange = {
                                                cardState.isSelected.value = !cardState.isSelected.value
                                                if (cardState.isSelected.value) {
                                                    onHomeEvent(HomeEvent.CardSelected(cardState.indexCard))
                                                }
                                                else {
                                                    onHomeEvent(HomeEvent.CardDeselected(cardState.indexCard))
                                                }
                                            },
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                        Text(
                                            text = "${cardState.indexCard.title} - ${cardState.indexCard.solution}",
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }

                                    Text(text = cardState.indexCard.category ?: "", modifier = Modifier.padding(end = 16.dp))
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        onHomeEvent(HomeEvent.StartTraining)
                        shouldShowBottomSheet.value = false
                    }) {
                        Text(text = stringResource(id = R.string.start_training))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                else if (modalBottomSheetState.currentValue.name == "PartiallyExpanded") {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {
                            onHomeEvent(HomeEvent.StartTraining)
                            shouldShowBottomSheet.value = false
                        }) {
                            Text(text = stringResource(id = R.string.start_training))
                        }
                        Text(text = stringResource(id = R.string.or))
                        Button(onClick = {
                            coroutineScope.launch {
                                modalBottomSheetState.expand()
                            }
                        }) {
                            Text(text = stringResource(id = R.string.add_cards))
                        }

                    }
                }

            }
        }
        SideEffect {
            coroutineScope.launch {
                modalBottomSheetState.partialExpand()

            }
        }
    } else if (shouldShowBottomSheet.value && loadAllCards().isEmpty()) {
        //Toast.makeText(LocalContext.current, R.string.no_cards_yet_title, Toast.LENGTH_LONG).show()
        onHomeEvent(HomeEvent.EmptyCards)
    }
}