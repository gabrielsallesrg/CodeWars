package com.gsrg.codewars.domain.di

import com.gsrg.codewars.domain.data.completed.CompletedChallengesRepository
import com.gsrg.codewars.domain.data.completed.ICompletedChallengesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class CompletedChallengesModule {

    @Binds
    abstract fun bindCompletedChallengesRepository(completedChallengesRepository: CompletedChallengesRepository): ICompletedChallengesRepository
}