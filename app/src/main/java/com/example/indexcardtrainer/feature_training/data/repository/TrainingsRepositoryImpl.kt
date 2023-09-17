package com.example.indexcardtrainer.feature_training.data.repository

import com.example.indexcardtrainer.feature_training.data.repository.database.TrainingsLogEntryDao
import com.example.indexcardtrainer.feature_training.domain.TrainingsLogEntry
import com.example.indexcardtrainer.feature_training.domain.repository.TrainingsRepository

class TrainingsRepositoryImpl(private val dao: TrainingsLogEntryDao) : TrainingsRepository {
    override suspend fun saveTrainingsLogEntry(logEntry: TrainingsLogEntry) {
        dao.saveTrainingsLogEntry(logEntry)
    }

    override suspend fun loadAllTrainingsLogEntries(): List<TrainingsLogEntry> {
        return dao.loadAllTrainingsLogEntries()
    }
}