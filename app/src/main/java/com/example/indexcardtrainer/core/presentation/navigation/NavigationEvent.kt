package com.example.indexcardtrainer.core.presentation.navigation

sealed class NavigationEvent {
    object OnHomeClick : NavigationEvent()
    object OnAllCardsClick : NavigationEvent()
    object OnStartTrainingClick : NavigationEvent()
    object OnTrainingsHistoryClick : NavigationEvent()
    object OnBackClick : NavigationEvent()
}
