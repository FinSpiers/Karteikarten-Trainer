package com.example.indexcardtrainer.core.presentation.navigation

sealed class Screen(var route : String) {
    object HomeScreen : Screen("Home")
    object AllCardsScreen : Screen("All cards")
    object TrainingsScreen : Screen("Training")
    object TrainingsHistoryScreen : Screen("Trainings history")
}
