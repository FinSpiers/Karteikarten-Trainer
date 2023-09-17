package com.example.indexcardtrainer.feature_all_cards.presentation.components

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.indexcardtrainer.R
import com.example.indexcardtrainer.core.domain.model.IndexCard

@Composable
fun DeleteCardDialog(
    shouldShowAlertDialog: MutableState<Boolean>,
    deleteCard: (IndexCard) -> Unit,
    getSelectedCard: () -> IndexCard?,
    resetSelectedCard : () -> Unit
) {
    if (shouldShowAlertDialog.value) {
        AlertDialog(
            onDismissRequest = { shouldShowAlertDialog.value = false },
            confirmButton = {
                Button(
                    onClick = {
                        val card = getSelectedCard()
                        if (card != null) {
                            deleteCard(card)
                            resetSelectedCard()
                        }
                        else {
                            Log.e("DEL", "Error on deleting")
                        }
                        shouldShowAlertDialog.value = false
                    },
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text(text = stringResource(id = R.string.delete))
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        shouldShowAlertDialog.value = false
                    },
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                ) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            shape = MaterialTheme.shapes.large,
            title = { Text(text = stringResource(id = R.string.delete_card), style = MaterialTheme.typography.displaySmall) },
            text = { Text(text = stringResource(id = R.string.delete_confirmation_text)) }
        )
    }
}