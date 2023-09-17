package com.example.indexcardtrainer.feature_all_cards.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.indexcardtrainer.R
import com.example.indexcardtrainer.core.domain.model.IndexCard
import com.example.indexcardtrainer.feature_all_cards.domain.SortingType
import com.example.indexcardtrainer.feature_all_cards.presentation.CardEvent

@Composable
fun CardsList(cards : List<IndexCard>, onCardEvent : (CardEvent) -> Unit, sortingType: MutableState<SortingType>) {
    if (cards.isNotEmpty()) {
        InfoAndSortingSection(
            cards.size,
            onCardEvent,
            sortingType.value
        )
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(cards) { card: IndexCard ->
                val context = LocalContext.current
                CardListItem(
                    card = card,
                    dropDownItems = listOf(
                        DropDownItem(stringResource(id = R.string.edit)),
                        DropDownItem(stringResource(id = R.string.delete)),
                        DropDownItem(stringResource(id = R.string.details))
                    ),
                    onClick = {
                        when (it.text) {
                            context.getString(R.string.edit) -> {
                                onCardEvent(CardEvent.CardEditing(card))
                            }
                            context.getString(R.string.delete) -> {
                                onCardEvent(CardEvent.CardDeletion(card))
                            }
                            context.getString(R.string.details) -> {
                                onCardEvent(CardEvent.CardDetails(card))
                            }
                        }
                    }
                )
            }
        }
    } else {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.no_cards_yet_title),
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.no_cards_yet_text),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

        }
    }
}