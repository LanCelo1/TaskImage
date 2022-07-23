package com.example.taskapp.di

import com.example.taskapp.domain.MainUseCase
import com.example.taskapp.domain.impl.MainUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindMainUseCase(useCase: MainUseCaseImpl) : MainUseCase
}