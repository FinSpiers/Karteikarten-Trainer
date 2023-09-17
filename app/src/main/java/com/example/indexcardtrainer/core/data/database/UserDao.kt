package com.example.indexcardtrainer.core.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.indexcardtrainer.core.domain.model.User

@Dao
interface UserDao {

    @Transaction
    @Query("SELECT * FROM User")
    fun loadUserData() : User?

    @Transaction
    @Upsert
    fun saveUserData(user : User)
}