package com.example.indexcardtrainer.feature_all_cards.presentation

import androidx.compose.material3.SnackbarHostState
import com.example.indexcardtrainer.core.data.repository.TestCardsRepository
import com.example.indexcardtrainer.core.data.repository.TestUserRepository
import com.example.indexcardtrainer.core.domain.model.IndexCard
import com.example.indexcardtrainer.feature_all_cards.domain.SortingType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
class AllCardsViewModelTest {
    private lateinit var allCardsViewModel: AllCardsViewModel
    private val userRepository = TestUserRepository()
    private val cardsRepository = TestCardsRepository()
    private val snackbarHostState = SnackbarHostState()
    private lateinit var cards : List<IndexCard>
    @Before
    fun setup() {
        allCardsViewModel = AllCardsViewModel(
            userRepository = userRepository,
            cardsRepository = cardsRepository,
            snackbarHostState = snackbarHostState
        )
        cards = cardsRepository.cards
        allCardsViewModel.cards.value = cards
    }

    @Test
    fun onSortingChange() {
        assertEquals(SortingType.ByCreation, allCardsViewModel.sortingType.value)
    }

    @Test
    fun onCardEvent() {
        // CardEvent.ChangeSortingType
        allCardsViewModel.onCardEvent(CardEvent.ChangeSortingType(SortingType.ByAlphabeticallyOrder))
        assertEquals(cards[0], allCardsViewModel.cards.value[0])
        assertEquals(cards[1], allCardsViewModel.cards.value[2])
        assertEquals(cards[2], allCardsViewModel.cards.value[1])

        allCardsViewModel.onCardEvent(CardEvent.ChangeSortingType(SortingType.ByCreation))
        assertEquals(cards[0], allCardsViewModel.cards.value[2])
        assertEquals(cards[1], allCardsViewModel.cards.value[1])
        assertEquals(cards[2], allCardsViewModel.cards.value[0])

        allCardsViewModel.onCardEvent(CardEvent.ChangeSortingType(SortingType.ByCategory))
        assertEquals(cards[0], allCardsViewModel.cards.value[0])
        assertEquals(cards[1], allCardsViewModel.cards.value[2])
        assertEquals(cards[2], allCardsViewModel.cards.value[1])

        // CardEvent.CardCreation
        allCardsViewModel.onCardEvent(CardEvent.CardCreation("Test", "test", "Test"))
        assertEquals(allCardsViewModel.cards.value[0].title, "Test")
        assertEquals(allCardsViewModel.cards.value[0].solution, "test")
        assertEquals(allCardsViewModel.cards.value[0].category, "Test")

        // CardEvent.CardEditing
        allCardsViewModel.onCardEvent(CardEvent.CardEditing(allCardsViewModel.cards.value[0]))
        assertEquals(allCardsViewModel.getSelectedCard(), allCardsViewModel.cards.value[0])
        assertTrue(allCardsViewModel.shouldShowEditCardDialog.value)
    }

}

