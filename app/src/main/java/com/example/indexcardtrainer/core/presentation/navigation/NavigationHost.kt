package com.example.indexcardtrainer.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.indexcardtrainer.feature_all_cards.presentation.AllCardsScreen
import com.example.indexcardtrainer.feature_home.presentation.HomeScreen
import com.example.indexcardtrainer.feature_training.presentation.TrainingsScreen
import com.example.indexcardtrainer.feature_trainingshistory.presentation.TrainingsHistoryScreen

@Composable
fun NavigationHost(navController : NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route,
        modifier = modifier
    ) {
        composable(Screen.HomeScreen.route) { HomeScreen() }
        composable(Screen.AllCardsScreen.route) { AllCardsScreen() }
        composable(Screen.TrainingsScreen.route) { TrainingsScreen() }
        composable(Screen.TrainingsHistoryScreen.route) { TrainingsHistoryScreen() }
    }
}