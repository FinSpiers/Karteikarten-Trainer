package com.example.indexcardtrainer.feature_training.data.repository.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.indexcardtrainer.feature_training.domain.TrainingsLogEntry

@Dao
interface TrainingsLogEntryDao {

    @Transaction
    @Upsert
    suspend fun saveTrainingsLogEntry(trainingsLogEntry: TrainingsLogEntry)

    @Transaction
    @Query("SELECT * FROM TrainingsLogs")
    suspend fun loadAllTrainingsLogEntries() : List<TrainingsLogEntry>
}