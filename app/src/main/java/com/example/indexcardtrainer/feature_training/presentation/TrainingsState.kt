package com.example.indexcardtrainer.feature_training.presentation

import com.example.indexcardtrainer.core.domain.model.IndexCard
import java.time.Instant
private val defaultCard = IndexCard(0,"", "")

data class TrainingsState(
    val cards: List<IndexCard> = listOf(defaultCard),
    val currentIndexCard: IndexCard = defaultCard,
    val timeUsedPerCard: List<Int> = emptyList(),
    val currentCardCorrectAnswered: Boolean = false,
    val currentCardWrongAnswered: Boolean = false,
    val currentMultiplicator: Int = 1,
    val correctAnsweredCards: MutableList<IndexCard> = mutableListOf(),
    val correctAnsweredStreak: Int = 0,
    val motivationText: String? = null,
    var rubberDotsEarned: Int = 0,
    val startTime: Long = Instant.now().epochSecond,
    val duration: Long = 0
)
