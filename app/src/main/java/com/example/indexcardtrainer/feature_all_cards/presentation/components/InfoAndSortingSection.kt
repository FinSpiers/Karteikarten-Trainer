
package com.example.indexcardtrainer.feature_all_cards.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.indexcardtrainer.R
import com.example.indexcardtrainer.feature_all_cards.domain.SortingType
import com.example.indexcardtrainer.feature_all_cards.presentation.CardEvent

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun InfoAndSortingSection(
    cardCount: Int,
    onCardEvent: (CardEvent) -> Unit,
    sortingType: SortingType
) {
    val sortByCreationChipSelected = remember { mutableStateOf(sortingType == SortingType.ByCreation) }
    val sortByCategoryChipSelected = remember { mutableStateOf(sortingType == SortingType.ByCategory) }
    val sortByAlphabeticallyChipSelected = remember { mutableStateOf(sortingType == SortingType.ByAlphabeticallyOrder) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.total_cards)
                )
                Text(
                    text = cardCount.toString(),
                    modifier = Modifier.padding(end = 8.dp),
                    fontWeight = FontWeight.Bold
                )
            }
            Text(text = stringResource(id = R.string.sort_by), modifier = Modifier.padding(start = 8.dp), fontStyle = FontStyle.Italic)

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilterChip(
                    selected = sortByCreationChipSelected.value,
                    onClick = {
                        onCardEvent(CardEvent.ChangeSortingType(SortingType.ByCreation))
                        sortByCreationChipSelected.value = true
                        sortByCategoryChipSelected.value = false
                        sortByAlphabeticallyChipSelected.value = false

                    },
                    label = { Text(text = stringResource(id = R.string.creation)) },
                )
                FilterChip(
                    selected = sortByCategoryChipSelected.value,
                    onClick = {
                        onCardEvent(CardEvent.ChangeSortingType(SortingType.ByCategory))
                        sortByCategoryChipSelected.value = true
                        sortByCreationChipSelected.value = false
                        sortByAlphabeticallyChipSelected.value = false
                    },
                    label = { Text(text = stringResource(id = R.string.category)) }
                )
                FilterChip(
                    selected = sortByAlphabeticallyChipSelected.value,
                    onClick = {
                        onCardEvent(CardEvent.ChangeSortingType(SortingType.ByAlphabeticallyOrder))
                        sortByAlphabeticallyChipSelected.value = true
                        sortByCreationChipSelected.value = false
                        sortByCategoryChipSelected.value = false
                    },
                    label = { Text(text = stringResource(id = R.string.alphabetically)) }
                )
            }
        }
    }
}