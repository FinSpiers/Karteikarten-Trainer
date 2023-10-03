package com.example.indexcardtrainer.core.presentation

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.indexcardtrainer.core.domain.repository.CardsRepository
import com.example.indexcardtrainer.core.domain.repository.UserRepository
import com.example.indexcardtrainer.core.presentation.navigation.NavigationEvent
import com.example.indexcardtrainer.core.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private var currentDestination  = mutableStateOf(Screen.HomeScreen.route)
    lateinit var navController : NavHostController

    init {
        cardsRepository.onNavigationEvent = this::onNavigationEvent
    }

    private fun onNavigationEvent(navigationEvent: NavigationEvent) {
        when(navigationEvent) {
            is NavigationEvent.OnHomeClick -> {
                navigate(Screen.HomeScreen.route)
            }
            is NavigationEvent.OnAllCardsClick -> {
                navigate(Screen.AllCardsScreen.route)
            }
            is NavigationEvent.OnStartTrainingClick -> {
                navigate(Screen.TrainingsScreen.route)
            }
            is NavigationEvent.OnTrainingsHistoryClick -> {
                navigate(Screen.TrainingsHistoryScreen.route)
            }
        }
    }

    fun setUserRepoContext(context : Context) {
        userRepository.context = context
    }

    private fun navigate(destination : String) {
        currentDestination.value = destination
        navController.navigate(destination) {
            popUpTo(destination)
            launchSingleTop = true
            restoreState = true
        }
    }
}