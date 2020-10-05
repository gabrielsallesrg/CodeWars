package com.gsrg.codewars.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gsrg.codewars.database.challenges.ChallengeCompleted
import com.gsrg.codewars.database.challenges.ChallengesDAO
import com.gsrg.codewars.database.players.Player
import com.gsrg.codewars.database.players.PlayersDao

@Database(
    entities = [Player::class, ChallengeCompleted::class],
    version = 1,
    exportSchema = false
)
abstract class CodeWarsDatabase : RoomDatabase() {

    abstract fun playersDao(): PlayersDao
    abstract fun challengesDao(): ChallengesDAO

    companion object {

        @Volatile
        private var INSTANCE: CodeWarsDatabase? = null

        fun getInstance(context: Context): CodeWarsDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CodeWarsDatabase::class.java,
                "CodeWars.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}