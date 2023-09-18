package com.example.indexcardtrainer.core.data.database

import androidx.room.*
import com.example.indexcardtrainer.core.domain.model.IndexCard

@Dao
interface CardDao {

    @Transaction
    @Query("SELECT * FROM IndexCards")
    suspend fun getAllIndexCards() : List<IndexCard>

    @Transaction
    @Query("SELECT category FROM IndexCards")
    suspend fun getAllCategories() : List<String>

    @Transaction
    @Query("SELECT * FROM IndexCards WHERE category=:cardCategory")
    suspend fun getIndexCardsWithCategory(cardCategory : String) : List<IndexCard>


    @Transaction
    @Query("SELECT * FROM IndexCards WHERE id=:cardId")
    suspend fun getIndexCardById(cardId : Int) : IndexCard?

    @Transaction
    @Upsert
    suspend fun upsertIndexCard(indexCard: IndexCard)

    @Transaction
    @Delete
    suspend fun deleteIndexCard(indexCard: IndexCard)

    @Transaction
    @Query("SELECT * FROM IndexCards WHERE isRecentlyFailed=${true}")
    suspend fun getRecentlyFailedCards() : List<IndexCard>

}