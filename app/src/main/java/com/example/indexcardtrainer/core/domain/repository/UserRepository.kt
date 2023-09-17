package com.example.indexcardtrainer.core.domain.repository

import android.content.Context
import com.example.indexcardtrainer.core.domain.model.User

interface UserRepository {
    var context : Context
    var calculateUserRank : (Int) -> Unit

    suspend fun loadUserData() : User

    suspend fun saveUserData(user: User)
}