package com.gsrg.codewars.domain.di

import com.gsrg.codewars.domain.data.challengedetails.ChallengeDetailsRepository
import com.gsrg.codewars.domain.data.challengedetails.IChallengeDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class ChallengeDetailsModule {

    @Binds
    abstract fun bindChallengeDetailsRepository(challengeDetailsRepository: ChallengeDetailsRepository): IChallengeDetailsRepository
}