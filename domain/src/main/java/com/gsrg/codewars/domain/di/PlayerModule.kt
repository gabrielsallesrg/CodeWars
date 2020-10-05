package com.gsrg.codewars.domain.di

import com.gsrg.codewars.domain.data.player.IPlayerRepository
import com.gsrg.codewars.domain.data.player.PlayerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class PlayerModule {

    @Binds
    abstract fun bindPlayerRepository(playerRepository: PlayerRepository): IPlayerRepository
}