package com.example.taskapp.di

import androidx.lifecycle.ViewModel
import com.example.taskapp.data.repository.AppRepository
import com.example.taskapp.data.repository.impl.AppRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindAppRepository(repository: AppRepositoryImpl) : AppRepository
}