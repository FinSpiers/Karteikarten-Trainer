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
    fun upsertIndexCard() = runTest {
        val indexCard = IndexCard(
            title = "Test",
            solution = "test",
            category = "Test"
        )
        async {
            cardDao.upsertIndexCard(indexCard)
        }.await()
        var cards : List<IndexCard> = emptyList()
        async {
            cards = cardDao.getAllIndexCards()
        }.await()
        println(">>>>>>>>>>>>>>>> $cards")
        //assertEquals(cards[0], indexCard)
        //assertTrue(cards.contains(indexCard))
    }
}