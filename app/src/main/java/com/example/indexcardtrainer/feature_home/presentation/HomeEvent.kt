package com.example.indexcardtrainer.feature_home.presentation

import com.example.indexcardtrainer.core.domain.model.IndexCard

sealed class HomeEvent {

    class CategorySelected(val category : String) : HomeEvent()

    class CategoryDeselected(val category : String) : HomeEvent()

    class CardSelected(val indexCard: IndexCard) : HomeEvent()

    class CardDeselected(val indexCard: IndexCard) : HomeEvent()

    class UserRankUp(val rank: String) : HomeEvent()

    data object StartTraining : HomeEvent()

    data object EmptyCards : HomeEvent()

    data object NoCardsSelected : HomeEvent()
}
