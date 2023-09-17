package com.example.indexcardtrainer.core.data.repository

import com.example.indexcardtrainer.core.data.database.CardDao
import com.example.indexcardtrainer.core.domain.model.IndexCard
import com.example.indexcardtrainer.core.domain.repository.CardsRepository
import com.example.indexcardtrainer.core.presentation.navigation.NavigationEvent
import kotlin.reflect.KFunction1

class CardsRepositoryImpl(private val cardDao: CardDao) : CardsRepository {
    override lateinit var onNavigationEvent: KFunction1<NavigationEvent, Unit>

    override var cardsToTrain: List<IndexCard> = emptyList()

    override suspend fun loadAllCardsFromDatabase(): List<IndexCard> {
        return cardDao.getAllIndexCards()
    }

    override suspend fun loadRecentlyFailedCards(): List<IndexCard> {
        return cardDao.getRecentlyFailedCards()
    }

    override suspend fun upsertIndexCard(indexCard: IndexCard) {
        cardDao.upsertIndexCard(indexCard)
    }

    override suspend fun getIndexCardById(cardId: Int): IndexCard? {
        return cardDao.getIndexCardById(cardId)
    }

    override suspend fun deleteIndexCard(indexCard: IndexCard) {
        try {
            cardDao.deleteIndexCard(indexCard)
        }
        catch (e : Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun loadAllCategoriesFromDatabase(): List<String> {
        return cardDao.getAllCategories().toSet().toList()
    }

    override suspend fun getIndexCardsWithCategory(category: String) : List<IndexCard> {
        return cardDao.getIndexCardsWithCategory(category)
    }
}