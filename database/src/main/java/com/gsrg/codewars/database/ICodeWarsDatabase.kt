package com.gsrg.codewars.database

import com.gsrg.codewars.database.challenges.AuthoredChallengeDao
import com.gsrg.codewars.database.challenges.ChallengeCompletedDao
import com.gsrg.codewars.database.challenges.ChallengeDetailsDao
import com.gsrg.codewars.database.challenges.CompletedRemoteKeysDao
import com.gsrg.codewars.database.players.PlayersDao

interface ICodeWarsDatabase {

    fun playersDao(): PlayersDao
    fun challengeCompletedDao(): ChallengeCompletedDao
    fun completedRemoteKeysDao(): CompletedRemoteKeysDao
    fun authoredChallengeDao(): AuthoredChallengeDao
    fun challengeDetailsDao(): ChallengeDetailsDao
}