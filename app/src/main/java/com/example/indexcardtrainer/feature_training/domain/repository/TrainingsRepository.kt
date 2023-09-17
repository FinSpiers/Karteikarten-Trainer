package com.example.indexcardtrainer.feature_training.domain.repository

import com.example.indexcardtrainer.feature_training.domain.TrainingsLogEntry

interface TrainingsRepository {

    suspend fun saveTrainingsLogEntry(logEntry: TrainingsLogEntry)

    suspend fun loadAllTrainingsLogEntries() : List<TrainingsLogEntry>
}