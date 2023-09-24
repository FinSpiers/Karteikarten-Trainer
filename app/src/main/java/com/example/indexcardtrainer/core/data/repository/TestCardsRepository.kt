package com.example.indexcardtrainer.core.data.repository

import com.example.indexcardtrainer.core.domain.model.IndexCard
import com.example.indexcardtrainer.core.domain.repository.CardsRepository
import com.example.indexcardtrainer.core.presentation.navigation.NavigationEvent
import kotlin.reflect.KFunction1

class TestCardsRepository : CardsRepository {
    override lateinit var onNavigationEvent: KFunction1<NavigationEvent, Unit>
    override var cardsToTrain: List<IndexCard> = arrayListOf()

    val cards = arrayListOf(
        IndexCard(
            id = 1,
            title = "A",
            solution = "A",
            timeStamp = 1
        ),
        IndexCard(
            id = 2,
            title = "C",
            solution = "C",
            category = "Test",
            isRecentlyFailed = true,
            timeStamp = 2
        ),
        IndexCard(
            id = 3,
            title = "B",
            solution = "B",
            category = "Test",
            correctAnsweredStreak = 7,
            timesCorrectAnswered = 7,
            timeStamp = 3
        )
    )
    val categories = listOf(
        "Test"
    )
    override suspend fun loadAllCardsFromDatabase(): List<IndexCard> {
        return cards
    }

    override suspend fun loadAllCategoriesFromDatabase(): List<String> {
        return categories
    }

    override suspend fun loadRecentlyFailedCards(): List<IndexCard> {
        return cards.filter { it.isRecentlyFailed }
    }

    override suspend fun upsertIndexCard(indexCard: IndexCard) {
        if (cards.find { it.id == indexCard.id } != null) {
            val index = cards.indexOf(cards.find { it.id == indexCard.id }!!)
            cards[index] = indexCard
        } else {
            cards.add(indexCard)
        }
    }

    override suspend fun getIndexCardById(cardId: Int): IndexCard? {
        return cards.find { it.id == cardId }
    }

    override suspend fun getIndexCardsWithCategory(category: String): List<IndexCard> {
        return cards.filter { it.category == category }
    }

    override suspend fun deleteIndexCard(indexCard: IndexCard) {
        cards.remove(indexCard)
    }
}