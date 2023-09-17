package com.example.indexcardtrainer.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.indexcardtrainer.core.domain.util.RANK_STARTER

@Entity(tableName = "User")
data class User(
    var username : String = "User",
    var rubberDots : Int = 0,
    var currentRank : String = RANK_STARTER
) {
    @PrimaryKey(autoGenerate = false)
    var userId : Int = 0
}
