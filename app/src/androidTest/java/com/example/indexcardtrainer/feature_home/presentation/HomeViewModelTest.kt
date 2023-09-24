package com.example.indexcardtrainer.feature_home.presentation

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.indexcardtrainer.core.data.repository.TestCardsRepository
import com.example.indexcardtrainer.core.data.repository.TestUserRepository
import com.example.indexcardtrainer.core.domain.util.RANK_STAFF_SERGEANT
import com.example.indexcardtrainer.feature_home.presentation.HomeViewModel
import com.example.indexcardtrainer.feature_home.presentation.states.CardSelectionState
import com.example.indexcardtrainer.feature_home.presentation.states.CategorySelectionState
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {
    private lateinit var viewModel : HomeViewModel
    @Before
    fun setup() {
        viewModel = HomeViewModel(
            cardsRepository = TestCardsRepository(),
            userRepository = TestUserRepository()
        )
    }

    @Test
    fun init() {
        val selectedCards = viewModel.cardSelectionStateList.filter { it.isSelected.value }
        val selectedCategories = viewModel.categorySelectionStateList.filter { it.isSelected.value }

        assertEquals(mutableListOf<CardSelectionState>(), selectedCards)
        assertEquals(mutableListOf<CategorySelectionState>(), selectedCategories)
    }

    @Test
    fun getRecentlyFailedCards() {
        val recentlyFailedCards = viewModel.getRecentlyFailedCards()
        assertEquals(1, recentlyFailedCards.size)
    }

    @Test
    fun loadAllCards() {
        val allCards = viewModel.loadAllCards()
        assertEquals(3, allCards.size)
    }

    @Test
    fun getUserRankName() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val userRankName = viewModel.getUserRankName(
            RANK_STAFF_SERGEANT,
            context
        )
    }


}