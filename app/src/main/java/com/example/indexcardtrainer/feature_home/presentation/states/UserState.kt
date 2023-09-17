package com.example.indexcardtrainer.feature_home.presentation.states

import com.example.indexcardtrainer.core.domain.util.RANK_STARTER

data class UserState(
    val username : String = "User",
    val rubberDots : Int = 0,
    val rank : String = RANK_STARTER
)
