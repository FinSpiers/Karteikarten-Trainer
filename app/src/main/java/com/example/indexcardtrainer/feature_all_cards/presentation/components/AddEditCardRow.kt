package com.example.indexcardtrainer.feature_all_cards.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.indexcardtrainer.R
import com.example.indexcardtrainer.core.presentation.IndexCardState

@Composable
fun AddEditCardRow(
    state : MutableState<IndexCardState>
) {
    val textState = remember {
        mutableStateOf(state.value.text)
    }
    val translationState = remember {
        mutableStateOf(state.value.translation)
    }
    val categoryState = remember {
        mutableStateOf(state.value.category)
    }
    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = textState.value,
            onValueChange = {
                textState.value = it
                state.value.text = textState.value
                            },
            label = { Text(text = stringResource(id = R.string.word)) },
            modifier = Modifier.weight(1f)
        )
        TextField(
            value = translationState.value,
            onValueChange = {
                translationState.value = it
                state.value.translation = translationState.value
                            },
            label = { Text(text = stringResource(id = R.string.translation)) },
            modifier = Modifier.weight(1f)
        )
        TextField(
            value = categoryState.value ?: "",
            onValueChange = {
                categoryState.value = it
                state.value.category = categoryState.value
                            },
            label = { Text(text = stringResource(id = R.string.category)) },
            modifier = Modifier.weight(1f)
        )
    }
}
