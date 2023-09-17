package com.example.indexcardtrainer.feature_training.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.indexcardtrainer.core.data.database.type_converter.IndexCardListConverter
import com.example.indexcardtrainer.core.data.database.type_converter.IntListConverter
import com.example.indexcardtrainer.core.domain.model.IndexCard

@Entity(tableName = "TrainingsLogs")
data class TrainingsLogEntry(
    @PrimaryKey(autoGenerate = false)
    val timestamp: Long,
    @TypeConverters(IndexCardListConverter::class)
    val cards: List<IndexCard>,
    @TypeConverters(IndexCardListConverter::class)
    val correctAnsweredCards: List<IndexCard>,
    @TypeConverters(IntListConverter::class)
    val timeUsedForEachCard : List<Int>,
    val duration: Long,
    val rubberDotsEarned: Int
)
