package com.example.indexcardtrainer.core.domain.repository

import com.example.indexcardtrainer.core.domain.model.IndexCard
import com.example.indexcardtrainer.core.presentation.navigation.NavigationEvent
import kotlin.reflect.KFunction1

interface CardsRepository {
    var onNavigationEvent: KFunction1<NavigationEvent, Unit>
    var cardsToTrain : List<IndexCard>

    suspend fun loadAllCardsFromDatabase() : List<IndexCard>

    suspend fun loadAllCategoriesFromDatabase() : List<String>

    suspend fun loadRecentlyFailedCards() : List<IndexCard>

    suspend fun upsertIndexCard(indexCard: IndexCard)

    suspend fun getIndexCardById(cardId : Int) : IndexCard?

    suspend fun getIndexCardsWithCategory(category : String) : List<IndexCard>

    suspend fun deleteIndexCard(indexCard: IndexCard)
}