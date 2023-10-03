package com.example.indexcardtrainer.feature_home.presentation

import android.content.Context
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.indexcardtrainer.R
import com.example.indexcardtrainer.core.domain.model.IndexCard
import com.example.indexcardtrainer.core.domain.model.User
import com.example.indexcardtrainer.core.domain.repository.CardsRepository
import com.example.indexcardtrainer.core.domain.repository.UserRepository
import com.example.indexcardtrainer.core.domain.util.RANK_CORPORAL
import com.example.indexcardtrainer.core.domain.util.RANK_MAJOR
import com.example.indexcardtrainer.core.domain.util.RANK_MASTER_SERGEANT
import com.example.indexcardtrainer.core.domain.util.RANK_PRIVATE
import com.example.indexcardtrainer.core.domain.util.RANK_PRIVATE_FIRST_CLASS
import com.example.indexcardtrainer.core.domain.util.RANK_SERGEANT
import com.example.indexcardtrainer.core.domain.util.RANK_SPECIALIST
import com.example.indexcardtrainer.core.domain.util.RANK_STAFF_SERGEANT
import com.example.indexcardtrainer.core.domain.util.RANK_STARTER
import com.example.indexcardtrainer.core.presentation.navigation.NavigationEvent
import com.example.indexcardtrainer.feature_home.presentation.states.CardSelectionState
import com.example.indexcardtrainer.feature_home.presentation.states.CategorySelectionState
import com.example.indexcardtrainer.feature_home.presentation.states.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(DelicateCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val userRepository: UserRepository,
    private val snackbarHostState: SnackbarHostState
) : ViewModel() {
    var userData: User = User()
    private lateinit var allCards: List<IndexCard>
    private lateinit var allCategories: List<String>
    private lateinit var recentlyFailedCards: List<IndexCard>
    val userState = mutableStateOf(UserState())

    val shouldShowTrainingsConfig = mutableStateOf(false)
    val shouldShowUserRankUpDialog = mutableStateOf(false)

    val cardSelectionStateList: MutableList<CardSelectionState> = mutableListOf()
    val categorySelectionStateList: MutableList<CategorySelectionState> = mutableListOf()

    init {
        userRepository.calculateUserRank = this::calculateUserRank
        refreshAnything()
    }

    fun refreshAnything() {
        resetSelection()
        viewModelScope.launch(Dispatchers.IO) {
            userData = userRepository.loadUserData()
            allCards = cardsRepository.loadAllCardsFromDatabase()
            allCards.forEach {
                cardSelectionStateList.add(CardSelectionState(it))
            }
            recentlyFailedCards = allCards.filter { it.isRecentlyFailed }
            allCategories = cardsRepository.loadAllCategoriesFromDatabase()
            allCategories.forEach {
                categorySelectionStateList.add(CategorySelectionState(it))
            }

            withContext(Dispatchers.Main) {
                userState.value = userState.value.copy(
                    username = userData.username,
                    rubberDots = userData.rubberDots,
                    rank = userData.currentRank
                )
            }
        }

    }

    fun onHomeEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.CardSelected -> {
                cardSelectionStateList.filter { it.indexCard == event.indexCard }
                    .forEach { it.isSelected.value = true }

                if (cardSelectionStateList.filter { it.indexCard.category == event.indexCard.category }
                        .all { it.isSelected.value }) {
                    categorySelectionStateList.filter { it.category == event.indexCard.category }
                        .forEach { it.isSelected.value = true }
                }
            }
            is HomeEvent.CardDeselected -> {
                cardSelectionStateList.filter { it.indexCard == event.indexCard }
                    .forEach { it.isSelected.value = false }

                if (cardSelectionStateList.filter { it.indexCard.category == event.indexCard.category }
                        .any { !it.isSelected.value }) {
                    categorySelectionStateList.filter { it.category == event.indexCard.category }
                        .forEach { it.isSelected.value = false }
                }
            }
            is HomeEvent.CategoryDeselected -> {
                categorySelectionStateList.filter { it.category == event.category }
                    .forEach { it.isSelected.value = false }
                cardSelectionStateList.filter { it.indexCard.category == event.category }.forEach {
                    it.isSelected.value = false
                }

            }
            is HomeEvent.CategorySelected -> {
                categorySelectionStateList.filter { it.category == event.category }
                    .forEach { it.isSelected.value = true }
                cardSelectionStateList.filter { it.indexCard.category == event.category }.forEach {
                    it.isSelected.value = true
                }
            }
            is HomeEvent.StartTraining -> {
                if (cardSelectionStateList.any { it.isSelected.value }) {
                    cardsRepository.cardsToTrain =
                        cardSelectionStateList.filter { it.isSelected.value }.map { it.indexCard }
                    cardsRepository.onNavigationEvent(NavigationEvent.OnStartTrainingClick)

                    viewModelScope.launch {
                        delay(500L)
                        resetSelection()
                    }
                }
                else {
                    onHomeEvent(HomeEvent.NoCardsSelected)
                }
            }
            is HomeEvent.UserRankUp -> {
                userData.currentRank = event.rank
                userState.value = userState.value.copy(
                    rank = event.rank
                )
                viewModelScope.launch(Dispatchers.IO) {
                    userRepository.saveUserData(userData)
                }
                shouldShowUserRankUpDialog.value = true
            }

            HomeEvent.EmptyCards -> {
                GlobalScope.launch {
                    val snackbarResult = snackbarHostState.showSnackbar(
                        message = userRepository.context.getString(R.string.no_cards_yet_title),
                        actionLabel = userRepository.context.getString(R.string.create_now),
                        withDismissAction = false,
                        SnackbarDuration.Long
                    )
                    withContext(Dispatchers.Main) {
                        when (snackbarResult) {
                            SnackbarResult.ActionPerformed ->
                                cardsRepository.onNavigationEvent(NavigationEvent.OnAllCardsClick)

                            else -> Unit
                        }
                    }
                }
            }

            HomeEvent.NoCardsSelected -> {
                viewModelScope.launch {
                    snackbarHostState.showSnackbar(
                        message = userRepository.context.getString(R.string.no_cards_selected),
                        actionLabel = null,
                        withDismissAction = false,
                        SnackbarDuration.Long
                    )
                }
            }
        }

    }

    private fun calculateUserRank(rubberDots: Int) {
        val currentRank = userData.currentRank
        val calculatedRank = when (rubberDots) {
            in 0..50 -> RANK_STARTER
            in 51..150 -> RANK_PRIVATE
            in 151..300 -> RANK_PRIVATE_FIRST_CLASS
            in 301..500 -> RANK_SPECIALIST
            in 501..750 -> RANK_CORPORAL
            in 751..1050 -> RANK_SERGEANT
            in 1051..1400 -> RANK_STAFF_SERGEANT
            in 1401..1800 -> RANK_MASTER_SERGEANT
            else -> RANK_MAJOR
        }
        userState.value = userState.value.copy(rubberDots = rubberDots, rank = calculatedRank)
        viewModelScope.launch(Dispatchers.IO) {
            userData.apply {
                this.rubberDots = rubberDots
                this.currentRank = calculatedRank
            }
            userRepository.saveUserData(userData)
        }
        if (currentRank != calculatedRank) {
            onHomeEvent(HomeEvent.UserRankUp(calculatedRank))
        }
    }


    fun getUserRankName(userRank: String, context: Context): String {
        return when (userRank) {
            RANK_STARTER -> context.getString(R.string.rank_recruit)
            RANK_PRIVATE -> context.getString(R.string.rank_private)
            RANK_PRIVATE_FIRST_CLASS -> context.getString(R.string.rank_private_first_class)
            RANK_SPECIALIST -> context.getString(R.string.rank_specialist)
            RANK_CORPORAL -> context.getString(R.string.rank_corporal)
            RANK_SERGEANT -> context.getString(R.string.rank_sergeant)
            RANK_STAFF_SERGEANT -> context.getString(R.string.rank_staff_sergeant)
            RANK_MASTER_SERGEANT -> context.getString(R.string.rank_master_sergeant)
            else -> context.getString(R.string.rank_major)
        }
    }

    private fun resetSelection() {
        cardSelectionStateList.clear()
        categorySelectionStateList.clear()
    }

    fun loadAllCards(): List<IndexCard> {
        val cards = mutableListOf<IndexCard>()
        runBlocking {
            cards.addAll(cardsRepository.loadAllCardsFromDatabase())
        }
        return cards
    }

    fun getRecentlyFailedCards(): List<IndexCard> {
        val cards: List<IndexCard>
        runBlocking {
            cards = cardsRepository.loadRecentlyFailedCards()
        }
        return cards
    }

    fun onNavigationEvent(navigationEvent: NavigationEvent) {
        cardsRepository.onNavigationEvent(navigationEvent)
    }
}