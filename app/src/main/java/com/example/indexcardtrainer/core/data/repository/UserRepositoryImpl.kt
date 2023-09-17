package com.example.indexcardtrainer.core.data.repository

import android.content.Context
import com.example.indexcardtrainer.core.data.database.UserDao
import com.example.indexcardtrainer.core.domain.model.User
import com.example.indexcardtrainer.core.domain.repository.UserRepository

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override lateinit var context: Context
    override lateinit var calculateUserRank: (Int) -> Unit

    override suspend fun loadUserData(): User {
        return userDao.loadUserData() ?: User("UnknownUser")
    }

    override suspend fun saveUserData(user: User) {
        userDao.saveUserData(user)
    }

}