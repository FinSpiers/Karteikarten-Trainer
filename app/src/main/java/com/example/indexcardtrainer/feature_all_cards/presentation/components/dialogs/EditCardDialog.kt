package com.example.indexcardtrainer.feature_all_cards.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
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
import com.example.indexcardtrainer.core.domain.model.IndexCard
import com.example.indexcardtrainer.core.presentation.IndexCardState

@Composable
fun EditCardDialog(
    shouldShowEditCardDialog: MutableState<Boolean>,
    getSelectedCard: () -> IndexCard?,
    saveCard : (IndexCard) -> Unit,
    resetSelectedCard: () -> Unit
) {
    if (shouldShowEditCardDialog.value) {
        val selectedCard = getSelectedCard()
        val editCardState = remember {
            mutableStateOf(selectedCard?.let {
                IndexCardState(
                    text = it.title,
                    translation = it.solution,
                    category = it.category
                )
            } ?: IndexCardState())
        }
        Dialog(
            onDismissRequest = { shouldShowEditCardDialog.value = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.large), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(id = R.string.edit_card), style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(16.dp))
                AddEditCardRow(state = editCardState)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    if (editCardState.value.text.isNotBlank() && editCardState.value.translation.isNotBlank()) {
                        Log.e("TEST", "Tried to save ${editCardState.value}")
                        selectedCard?.apply {
                            title = editCardState.value.text
                            solution = editCardState.value.translation
                            category = editCardState.value.category
                        }?.let { saveCard(it) }
                        resetSelectedCard()
                        shouldShowEditCardDialog.value = false
                    }
                }) {
                    Text(text = stringResource(id = R.string.save_card))
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

}