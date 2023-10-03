package com.example.indexcardtrainer.feature_training.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.indexcardtrainer.R
import com.example.indexcardtrainer.core.domain.model.IndexCard
import com.example.indexcardtrainer.core.domain.model.User
import com.example.indexcardtrainer.core.domain.repository.CardsRepository
import com.example.indexcardtrainer.core.domain.repository.UserRepository
import com.example.indexcardtrainer.core.presentation.navigation.NavigationEvent
import com.example.indexcardtrainer.feature_training.domain.TrainingsLogEntry
import com.example.indexcardtrainer.feature_training.domain.repository.TrainingsRepository
import com.example.indexcardtrainer.feature_training.domain.util.Timer
import com.example.indexcardtrainer.feature_training.presentation.TrainingsEvent.CorrectAnswered
import com.example.indexcardtrainer.feature_training.presentation.TrainingsEvent.FinishTraining
import com.example.indexcardtrainer.feature_training.presentation.TrainingsEvent.NextCard
import com.example.indexcardtrainer.feature_training.presentation.TrainingsEvent.StartTraining
import com.example.indexcardtrainer.feature_training.presentation.TrainingsEvent.WrongAnswered
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.time.Instant
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class TrainingsViewModel @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val userRepository: UserRepository,
    private val trainingsRepository: TrainingsRepository

) : ViewModel() {
    private val trainingsState: MutableState<TrainingsState> = mutableStateOf(TrainingsState())
    val trainingsStateFlow: MutableStateFlow<TrainingsState> =
        MutableStateFlow(trainingsState.value)

    val shouldShowTrainingFinishedDialog = mutableStateOf(false)
    lateinit var user: User
    private val timer : Timer = Timer()

    init {
        onTrainingEvent(StartTraining)
    }

    fun calculateUserRank(rubberDots: Int) {
        userRepository.calculateUserRank(rubberDots)
    }

    fun onNavigationEvent(navigationEvent: NavigationEvent) {
        cardsRepository.onNavigationEvent(navigationEvent)
    }

    private fun calculateElapsedTime(indexCard: IndexCard) {
        val index = trainingsState.value.cards.indexOf(indexCard)
        val timeUsedPerCardCopy = arrayListOf<Int>()
        timeUsedPerCardCopy.addAll(trainingsState.value.timeUsedPerCard)

        timeUsedPerCardCopy.add(index, timer.timerRuntimeFlow.value.toInt())
        trainingsState.value = trainingsState.value.copy(
            timeUsedPerCard = timeUsedPerCardCopy
        )
        trainingsStateFlow.tryEmit(trainingsState.value)
    }

    private fun randomizeCardOrder() {
        if (trainingsState.value.cards.size >= 2) {
            for (i in 0 until trainingsState.value.cards.size) {
                val randIndex = Random.nextInt(0, trainingsState.value.cards.size - 1)
                val randSwapIndex = Random.nextInt(0, trainingsState.value.cards.size - 1)

                trainingsState.value = trainingsState.value.copy(
                    cards = swapCards(trainingsState.value.cards, randIndex, randSwapIndex)
                )
            }
            trainingsState.value = trainingsState.value.copy(
                currentIndexCard = trainingsState.value.cards[0]
            )
        }
    }

    private fun swapCards(indexCards: List<IndexCard>, index : Int, swapIndex : Int) : List<IndexCard> {
        val cards = arrayListOf<IndexCard>()
        cards.addAll(indexCards)
        if (index in indexCards.indices && swapIndex in indexCards.indices) {
            val card = indexCards[index]
            cards[index] = indexCards[swapIndex]
            cards[swapIndex] = card
            return cards
        }
        return indexCards
    }

    fun loadUserData() : User {
        lateinit var user : User
        runBlocking {
            user = userRepository.loadUserData()
        }
        return user
    }

    fun onTrainingEvent(trainingsEvent: TrainingsEvent) {
        when (trainingsEvent) {
            is StartTraining    -> onTrainingStarted()
            is CorrectAnswered  -> onCardCorrectAnswered()
            is WrongAnswered    -> onCardWrongAnswered()
            is NextCard         -> onNextCard()
            is FinishTraining   -> onTrainingFinished()
        }
    }

    private fun onTrainingStarted() {
        timer.start()
        viewModelScope.launch {
            trainingsState.value = trainingsState.value.copy(
                cards = cardsRepository.cardsToTrain,
                currentIndexCard = cardsRepository.cardsToTrain[0],
                startTime = Instant.now().epochSecond
            )
            randomizeCardOrder()
            trainingsStateFlow.emit(trainingsState.value)
            withContext(Dispatchers.IO) {
                user = userRepository.loadUserData()
            }
        }
    }

    private fun onCardCorrectAnswered() {
        timer.stop()
        calculateElapsedTime(trainingsState.value.currentIndexCard)
        trainingsState.value = trainingsState.value.copy(
            currentCardCorrectAnswered = true,
            correctAnsweredCards = trainingsState.value.correctAnsweredCards.apply {
                add(
                    trainingsState.value.currentIndexCard
                )
            },
            correctAnsweredStreak = trainingsState.value.correctAnsweredStreak + 1,
            rubberDotsEarned = trainingsState.value.rubberDotsEarned + (trainingsState.value.currentMultiplicator * 10),
            motivationText = userRepository.context.getString(R.string.motivation_text_good_job_go_on)
        )
        viewModelScope.launch(Dispatchers.IO) {
            trainingsState.value = trainingsState.value.copy(
                currentMultiplicator = trainingsState.value.currentMultiplicator + 1,
                currentIndexCard = trainingsState.value.currentIndexCard.apply {
                    this.totalAttempts += 1
                    this.timesCorrectAnswered += 1
                    this.isRecentlyFailed = false
                    this.correctAnsweredStreak += 1
                }
            )
            cardsRepository.upsertIndexCard(trainingsState.value.currentIndexCard)
            trainingsStateFlow.emit(trainingsState.value)
            delay(2000L)
            onTrainingEvent(NextCard)
        }
    }

    private fun onCardWrongAnswered() {
        timer.stop()
        calculateElapsedTime(trainingsState.value.currentIndexCard)
        trainingsState.value = trainingsState.value.copy(
            currentCardWrongAnswered = true,
            correctAnsweredStreak = 0,
            currentMultiplicator = 1,
            motivationText = "${userRepository.context.getString(R.string.motivation_text_correct_answer)}: ${trainingsState.value.currentIndexCard.solution}",
            currentIndexCard = trainingsState.value.currentIndexCard.apply {
                this.isRecentlyFailed = true
                this.totalAttempts += 1
                this.correctAnsweredStreak = 0
            }
        )
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                cardsRepository.upsertIndexCard(trainingsState.value.currentIndexCard)
            }
            trainingsStateFlow.emit(trainingsState.value)
            delay(5000L)
            onTrainingEvent(NextCard)
        }
    }

    private fun onNextCard() {
        timer.reset()
        val index =
            trainingsState.value.cards.indexOf(trainingsState.value.currentIndexCard)
        if (index < trainingsState.value.cards.size - 1) {
            val motivationText: String? =
                if (trainingsState.value.currentIndexCard.correctAnsweredStreak > 1) userRepository.context.getString(
                    R.string.motivation_text_streak_detected
                ) else null
            trainingsState.value = trainingsState.value.copy(
                currentIndexCard = trainingsState.value.cards[index + 1],
                currentCardCorrectAnswered = false,
                currentCardWrongAnswered = false,
                motivationText = motivationText,
                currentMultiplicator = trainingsState.value.currentMultiplicator * if (trainingsState.value.currentIndexCard.correctAnsweredStreak > 1) 2 else 1
            )
        } else {
            onTrainingEvent(FinishTraining)
        }
        viewModelScope.launch {
            trainingsStateFlow.emit(trainingsState.value)
        }
    }

    private fun onTrainingFinished() {
        viewModelScope.launch(Dispatchers.IO) {
            trainingsStateFlow.emit(trainingsState.value)
            trainingsState.value.cards.forEach {
                cardsRepository.upsertIndexCard(it)
            }
            user.apply { rubberDots += trainingsState.value.rubberDotsEarned }
            userRepository.calculateUserRank(user.rubberDots)
            userRepository.saveUserData(user)

            trainingsRepository.saveTrainingsLogEntry(
                TrainingsLogEntry(
                    trainingsState.value.startTime,
                    cards = trainingsState.value.cards,
                    correctAnsweredCards = trainingsState.value.correctAnsweredCards,
                    timeUsedForEachCard = trainingsState.value.timeUsedPerCard,
                    duration = trainingsState.value.timeUsedPerCard.sum().toLong(),
                    rubberDotsEarned = trainingsState.value.rubberDotsEarned
                )
            )
        }
        shouldShowTrainingFinishedDialog.value = true
        cardsRepository.cardsToTrain = emptyList()
    }
}