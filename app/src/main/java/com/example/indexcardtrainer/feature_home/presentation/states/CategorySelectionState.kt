package com.example.indexcardtrainer.feature_home.presentation.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class CategorySelectionState(
    val category: String?,
    var isSelected: MutableState<Boolean> = mutableStateOf(false)
)