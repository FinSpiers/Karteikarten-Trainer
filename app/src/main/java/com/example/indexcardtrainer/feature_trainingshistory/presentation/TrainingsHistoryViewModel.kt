package com.example.indexcardtrainer.feature_trainingshistory.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.indexcardtrainer.core.data.repository.TestCardsRepository
import com.example.indexcardtrainer.core.domain.repository.CardsRepository
import com.example.indexcardtrainer.core.domain.util.DateTimeConverter
import com.example.indexcardtrainer.core.presentation.navigation.NavigationEvent
import com.example.indexcardtrainer.feature_training.domain.TrainingsLogEntry
import com.example.indexcardtrainer.feature_training.domain.repository.TrainingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class TrainingsHistoryViewModel @Inject constructor(
    private val trainingsRepository: TrainingsRepository,
    private val cardsRepository: CardsRepository
) : ViewModel() {

    fun calculateDaysSinceLastTraining(): Int {
        var daysSinceLastTraining = 0
        runBlocking {
            val trainingsLogEntries = trainingsRepository.loadAllTrainingsLogEntries().reversed()
            if (trainingsLogEntries.isNotEmpty()) {
                val lastLog = trainingsLogEntries[0]
                val lastTrainingDate = DateTimeConverter.parseDateTime(lastLog.timestamp).toLocalDate()
                val now = DateTimeConverter.parseDateTime(Instant.now().epochSecond).toLocalDate()
                val timeDivSeconds = Instant.now().epochSecond - lastLog.timestamp
                Log.e("TIME", DateTimeConverter.parseDateTime(lastLog.timestamp).toString())
                Log.e("TIME", (timeDivSeconds / 60).toString())
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
        // Return reversed list to display most recent logs on top
        return trainingsLogEntries.reversed()
    }

    fun onNavigationEvent(navigationEvent: NavigationEvent) {
        cardsRepository.onNavigationEvent(navigationEvent)
    }

}