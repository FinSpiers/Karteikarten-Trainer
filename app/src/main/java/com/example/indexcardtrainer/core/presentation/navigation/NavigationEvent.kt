package com.example.indexcardtrainer.core.presentation.navigation

sealed class NavigationEvent {
    data object OnHomeClick : NavigationEvent()
    data object OnAllCardsClick : NavigationEvent()
    data object OnStartTrainingClick : NavigationEvent()
    data object OnTrainingsHistoryClick : NavigationEvent()
}
