package com.example.indexcardtrainer.feature_trainingshistory.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.indexcardtrainer.feature_training.domain.TrainingsLogEntry
import com.example.indexcardtrainer.feature_training.domain.repository.TrainingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class TrainingsHistoryViewModel @Inject constructor(
    private val trainingsRepository: TrainingsRepository
) : ViewModel() {

    fun calculateDaysSinceLastTraining(): Int {
        var daysSinceLastTraining = 0
        viewModelScope.launch {
            val trainingsLogEntries = trainingsRepository.loadAllTrainingsLogEntries()
            if (trainingsLogEntries.isNotEmpty()) {
                val lastLog = trainingsLogEntries.sortedByDescending { it.timestamp }[0]
                daysSinceLastTraining =
                    ((Instant.now().epochSecond - lastLog.timestamp) / 3600 / 24).toInt()
            }
        }
        return daysSinceLastTraining
    }

    fun loadAllTrainingsLogEntries() : List<TrainingsLogEntry> {
        val trainingsLogEntries = arrayListOf<TrainingsLogEntry>()
        runBlocking {
            trainingsLogEntries.addAll(trainingsRepository.loadAllTrainingsLogEntries())
        }
        return trainingsLogEntries
    }

}