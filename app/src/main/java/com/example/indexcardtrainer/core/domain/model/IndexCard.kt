package com.example.indexcardtrainer.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "IndexCards")
data class IndexCard(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var title : String = "",
    var solution : String = "",
    var category : String? = null,
    var timeStamp : Long = Instant.now().epochSecond,
    var totalAttempts : Int = 0,
    var timesCorrectAnswered : Int = 0,
    var correctAnsweredStreak : Int = 0,
    var isRecentlyFailed : Boolean = false,
    var isOftenFailed : Boolean = false,
    var personalBestTime : Int = 0

)
