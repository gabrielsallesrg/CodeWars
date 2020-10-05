package com.gsrg.codewars.domain.di

import com.gsrg.codewars.domain.data.CompletedChallengesRepository
import com.gsrg.codewars.domain.data.ICompletedChallengesRepository
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