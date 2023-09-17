package com.example.indexcardtrainer.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.indexcardtrainer.core.data.database.LocalDatabase
import com.example.indexcardtrainer.core.data.repository.CardsRepositoryImpl
import com.example.indexcardtrainer.core.data.repository.UserRepositoryImpl
import com.example.indexcardtrainer.core.domain.repository.CardsRepository
import com.example.indexcardtrainer.core.domain.repository.UserRepository
import com.example.indexcardtrainer.feature_training.data.repository.TrainingsRepositoryImpl
import com.example.indexcardtrainer.feature_training.domain.repository.TrainingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(app : Application) : LocalDatabase {
        return Room.databaseBuilder(app, LocalDatabase::class.java, "LocalDatabase").build()
    }

    @Provides
    fun provideContext(app : Application) : Context {
        return app
    }

    @Provides
    @Singleton
    fun provideCardsRepository(
        database: LocalDatabase
    ) : CardsRepository {
        return CardsRepositoryImpl(database.cardDao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        database: LocalDatabase
    ) : UserRepository {
        return UserRepositoryImpl(database.userDao)
    }

    @Provides
    @Singleton
    fun provideTrainingsRepository(
        database: LocalDatabase
    ) : TrainingsRepository {
        return TrainingsRepositoryImpl(database.trainingsLogEntryDao)
    }
}