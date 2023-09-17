package com.example.indexcardtrainer.feature_all_cards.domain

sealed class SortingType {
    object ByCreation : SortingType()
    object ByCategory : SortingType()
    object ByAlphabeticallyOrder : SortingType()
}
