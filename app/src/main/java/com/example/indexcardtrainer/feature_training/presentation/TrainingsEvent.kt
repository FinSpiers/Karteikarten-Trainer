package com.example.indexcardtrainer.feature_training.presentation

sealed class TrainingsEvent {
    object StartTraining : TrainingsEvent()

    object CorrectAnswered : TrainingsEvent()

    object WrongAnswered : TrainingsEvent()

    object NextCard : TrainingsEvent()

    object FinishTraining : TrainingsEvent()
}
