package com.gsrg.codewars.domain.di

import com.gsrg.codewars.domain.authored.AuthoredChallengesRepository
import com.gsrg.codewars.domain.authored.IAuthoredChallengesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class AuthoredChallengesModule {

    @Binds
    abstract fun bindAuthoredChallengeRepository(authoredChallengesRepository: AuthoredChallengesRepository): IAuthoredChallengesRepository
}