package com.example.indexcardtrainer.feature_training.presentation

sealed class TrainingsEvent {
    data object StartTraining : TrainingsEvent()

    data object CorrectAnswered : TrainingsEvent()

    data object WrongAnswered : TrainingsEvent()

    data object NextCard : TrainingsEvent()

    data object FinishTraining : TrainingsEvent()
}
