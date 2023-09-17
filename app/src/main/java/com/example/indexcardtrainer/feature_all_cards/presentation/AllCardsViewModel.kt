package com.example.indexcardtrainer.feature_all_cards.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.indexcardtrainer.core.domain.model.IndexCard
import com.example.indexcardtrainer.core.domain.repository.CardsRepository
import com.example.indexcardtrainer.feature_all_cards.domain.SortingType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class AllCardsViewModel @Inject constructor(
    private val cardsRepository: CardsRepository
) : ViewModel() {
    val sortingType : MutableState<SortingType> = mutableStateOf(SortingType.ByCreation)
    val shouldShowAddCardsDialog = mutableStateOf(false)
    val shouldShowEditCardDialog = mutableStateOf(false)
    val shouldShowDeleteAlertDialog = mutableStateOf(false)
    val shouldShowCardDetailsDialog = mutableStateOf(false)

    val cards : MutableState<List<IndexCard>> = mutableStateOf(emptyList())
    private var selectedCard : IndexCard? = null

    private fun onSortingChange(sortingType: SortingType) {
        this.sortingType.value = sortingType
        cards.value = when(sortingType) {
            is SortingType.ByCreation -> {
                cards.value.sortedBy { it.timeStamp }.toMutableList()
            }
            is SortingType.ByCategory -> {
                cards.value.sortedBy { it.category }.toMutableList()
            }
            is SortingType.ByAlphabeticallyOrder -> {
                cards.value.sortedBy { it.title }.toMutableList()
            }
        }

    }

    fun getSelectedCard() : IndexCard? {
        return selectedCard
    }

    init {
        viewModelScope.launch {
            cards.value = cardsRepository.loadAllCardsFromDatabase()
            onSortingChange(sortingType.value)
        }
    }

    fun resetSelectedCard() {
        selectedCard = null
    }

    fun saveIndexCard(indexCard: IndexCard) {
        // Check if card already exists (aka update)
        if (cards.value.any { card -> card.id == indexCard.id }) {
            val cardIndex = cards.value.indexOf(indexCard)
            cards.value = cards.value.toMutableList().apply { remove(indexCard) }
            cards.value = cards.value.toMutableList().apply { add(cardIndex, indexCard) }
        }
        viewModelScope.launch(Dispatchers.IO) {
            cardsRepository.upsertIndexCard(indexCard)
        }
    }

    fun onCardEvent(cardEvent: CardEvent) {
        when(cardEvent) {
            is CardEvent.CardCreation -> {
                IndexCard(
                    title = cardEvent.value,
                    solution = cardEvent.solution,
                    category = cardEvent.category,
                    timeStamp = Instant.now().epochSecond
                ).also {
                    val newCards = cards.value.toMutableList().apply { add(0, it) }.toList()
                    cards.value = newCards
                    viewModelScope.launch(Dispatchers.IO) {
                        cardsRepository.upsertIndexCard(it)
                    }
                }
            }
            is CardEvent.CardEditing -> {
                selectedCard = cardEvent.card
                shouldShowEditCardDialog.value = true
            }
            is CardEvent.CardDeletion -> {
                selectedCard = cardEvent.card
                shouldShowDeleteAlertDialog.value = true
            }
            is CardEvent.ChangeSortingType -> {
                onSortingChange(cardEvent.sortingType)
            }
            is CardEvent.CardDetails -> {
                selectedCard = cardEvent.card
                shouldShowCardDetailsDialog.value = true
            }
        }
    }

    fun deleteIndexCard(indexCard: IndexCard) {
        cards.value = cards.value.toMutableList().apply { remove(indexCard) }
        viewModelScope.launch(Dispatchers.IO) {
            cardsRepository.deleteIndexCard(indexCard)
        }
    }
}