package com.example.indexcardtrainer.feature_all_cards.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.indexcardtrainer.R
import com.example.indexcardtrainer.feature_all_cards.presentation.components.*
import com.example.indexcardtrainer.ui.theme.IndexCardTrainerTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AllCardsScreen(viewModel: AllCardsViewModel = hiltViewModel()) {
    /***************************** Dialogs ************************************************/
    EditCardDialog(shouldShowEditCardDialog = viewModel.shouldShowEditCardDialog, getSelectedCard = viewModel::getSelectedCard, viewModel::saveIndexCard, viewModel::resetSelectedCard)

    AddCardsDialog(shouldShow = viewModel.shouldShowAddCardsDialog, onCardEvent = viewModel::onCardEvent)

    CardDetailsDialog(shouldShowCardDetailsDialog = viewModel.shouldShowCardDetailsDialog, getSelectedCard = viewModel::getSelectedCard, viewModel::resetSelectedCard)

    /**************************** Screen *************************************************/
    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = { viewModel.shouldShowAddCardsDialog.value = true },
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add card")
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.shapes.medium)
                    .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.medium)
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.your_cards),
                    style = MaterialTheme.typography.headlineLarge,
                    maxLines = 1,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            CardsList(
                cards = viewModel.cards.value,
                onCardEvent = viewModel::onCardEvent,
                sortingType = viewModel.sortingType
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun CardScreenPreview() {
    IndexCardTrainerTheme {
        AllCardsScreen()
    }
}