package com.example.indexcardtrainer.feature_home.presentation.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.indexcardtrainer.core.domain.model.IndexCard

data class CardSelectionState(
    val indexCard : IndexCard,
    var isSelected : MutableState<Boolean> = mutableStateOf(false)
)
