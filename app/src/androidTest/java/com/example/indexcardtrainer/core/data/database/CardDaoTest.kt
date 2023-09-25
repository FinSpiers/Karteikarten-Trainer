@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.indexcardtrainer.core.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.indexcardtrainer.core.domain.model.IndexCard
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class CardDaoTest {
    private lateinit var database : LocalDatabase
    private lateinit var cardDao: CardDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LocalDatabase::class.java
        ).allowMainThreadQueries().build()

        cardDao = database.cardDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun getAllIndexCards() = runTest {
        var cards : List<IndexCard> = emptyList()
        val indexCard = IndexCard(
            id = 1,
            title = "Test",
            solution = "test",
            category = "Test"
        )
        async {
            cardDao.upsertIndexCard(indexCard)
        }.await()
        async {
            cards = cardDao.getAllIndexCards()
        }.await()
        assertEquals(listOf(indexCard), cards)
    }

    @Test
    fun upsertIndexCard() = runTest {
        var cards : List<IndexCard> = emptyList()
        val indexCard = IndexCard(
            id = 1,
            title = "Test",
            solution = "test",
            category = "Test"
        )
        async {
            cards = cardDao.getAllIndexCards()
        }.await()
        assertFalse(cards.contains(indexCard))

        // Insert
        async {
            cardDao.upsertIndexCard(indexCard)
        }.await()
        async {
            cards = cardDao.getAllIndexCards()
        }.await()
        assertEquals(cards[0], indexCard)

        // Update
        indexCard.apply {
            title = "Test2"
            solution = "test2"
            category = "Test2"
        }
        async {
            cardDao.upsertIndexCard(indexCard)
        }.await()
        async {
            cards = cardDao.getAllIndexCards()
        }.await()
        assertEquals(cards[0].title, "Test2")
        assertEquals(cards[0].solution, "test2")
        assertEquals(cards[0].category, "Test2")
    }

    @Test
    fun deleteIndexCard() = runTest {
        var cards : List<IndexCard> = emptyList()
        val indexCard = IndexCard(
            id = 1,
            title = "Test",
            solution = "test",
            category = "Test"
        )
        async {
            cardDao.upsertIndexCard(indexCard)
        }.await()
        async {
            cards = cardDao.getAllIndexCards()
        }.await()
        assertTrue(cards.contains(indexCard))

        // Delete
        async {
            cardDao.deleteIndexCard(indexCard)
        }.await()
        async {
            cards = cardDao.getAllIndexCards()
        }.await()
        assertFalse(cards.contains(indexCard))
    }

    @Test
    fun getRecentlyFailedCards() = runTest {
        var recentlyFailedCards : List<IndexCard> = emptyList()
        val indexCard = IndexCard(
            id = 1,
            title = "Test",
            solution = "test",
            category = "Test",
            isRecentlyFailed = true
        )
        async {
            cardDao.upsertIndexCard(indexCard)
        }.await()
        async {
            recentlyFailedCards = cardDao.getRecentlyFailedCards()
        }.await()
        assertTrue(recentlyFailedCards.contains(indexCard))
    }

    @Test
    fun getIndexCardById() = runTest {
        var cardById : IndexCard? = null
        val indexCard = IndexCard(
            id = 1,
            title = "Test",
            solution = "test",
            category = "Test",
            isRecentlyFailed = true
        )
        async {
            cardDao.upsertIndexCard(indexCard)
        }.await()
        async {
            cardById = cardDao.getIndexCardById(1)
        }.await()
        assertEquals(indexCard, cardById)
    }

    @Test
    fun getIndexCardsWithCategory() = runTest {
        var cardsByCategory : List<IndexCard> = emptyList()
        val indexCard = IndexCard(
            id = 1,
            title = "Test",
            solution = "test",
            category = "Test",
            isRecentlyFailed = true
        )
        async {
            cardDao.upsertIndexCard(indexCard)
        }.await()
        async {
            cardsByCategory = cardDao.getIndexCardsWithCategory("Test")
        }.await()
        assertTrue(cardsByCategory.contains(indexCard))
    }

    @Test
    fun getAllCategories() = runTest {
        var categories : List<String> = emptyList()
        val indexCard = IndexCard(
            id = 1,
            title = "Test",
            solution = "test",
            category = "Test",
            isRecentlyFailed = true
        )
        async {
            cardDao.upsertIndexCard(indexCard)
        }.await()
        async {
            categories = cardDao.getAllCategories()
        }.await()
        assertEquals(listOf("Test"), categories)
    }
}