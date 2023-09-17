package com.example.indexcardtrainer.core.data.repository

import android.content.Context
import com.example.indexcardtrainer.core.domain.model.User
import com.example.indexcardtrainer.core.domain.repository.UserRepository
import com.example.indexcardtrainer.core.domain.util.RANK_STAFF_SERGEANT

class TestUserRepository : UserRepository {
    override lateinit var context: Context
    private var user = User(
        "TestUser",
        1337,
        RANK_STAFF_SERGEANT
    )

    override var calculateUserRank: (Int) -> Unit = {
        println("Calculate user rank with param: $it")
    }

    override suspend fun loadUserData(): User {
        return user
    }

    override suspend fun saveUserData(user: User) {
        this.user = user
    }
}