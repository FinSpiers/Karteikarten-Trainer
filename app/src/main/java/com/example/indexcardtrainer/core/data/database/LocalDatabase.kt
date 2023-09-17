package com.example.indexcardtrainer.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.indexcardtrainer.core.data.database.type_converter.IndexCardListConverter
import com.example.indexcardtrainer.core.data.database.type_converter.IntListConverter
import com.example.indexcardtrainer.core.domain.model.IndexCard
import com.example.indexcardtrainer.core.domain.model.User
import com.example.indexcardtrainer.feature_training.data.repository.database.TrainingsLogEntryDao
import com.example.indexcardtrainer.feature_training.domain.TrainingsLogEntry

@Database(entities = [IndexCard::class, User::class, TrainingsLogEntry::class], version = 1)
@TypeConverters(IndexCardListConverter::class, IntListConverter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract val cardDao : CardDao

    abstract val userDao : UserDao

    abstract val trainingsLogEntryDao : TrainingsLogEntryDao
}
