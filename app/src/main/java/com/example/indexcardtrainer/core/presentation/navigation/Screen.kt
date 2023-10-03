package com.example.indexcardtrainer.core.presentation.navigation

sealed class Screen(var route : String) {
    data object HomeScreen : Screen("Home")
    data object AllCardsScreen : Screen("All cards")
    data object TrainingsScreen : Screen("Training")
    data object TrainingsHistoryScreen : Screen("Trainings history")
}
