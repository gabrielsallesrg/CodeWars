package com.gsrg.codewars.database.challenges

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.gsrg.codewars.database.CodeWarsDatabase
import com.gsrg.codewars.database.players.Player
import com.gsrg.codewars.database.players.PlayersDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class ChallengeDetailsDaoTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var playersDao: PlayersDao
    private lateinit var challengeDetailsDao: ChallengeDetailsDao
    private lateinit var codeWarsDatabase: CodeWarsDatabase

    @Before
    fun createDb() {
        Dispatchers.setMain(testDispatcher)
        val context = ApplicationProvider.getApplicationContext<Context>()
        codeWarsDatabase = Room.inMemoryDatabaseBuilder(
            context, CodeWarsDatabase::class.java
        ).build()
        playersDao = codeWarsDatabase.playersDao()
        challengeDetailsDao = codeWarsDatabase.challengeDetailsDao()
    }

    @After
    @Throws(IOException::class)
    fun deleteDataAndCloseDatabase() {
        codeWarsDatabase.close()
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun insertAndRemoveChallengeDetails() = runBlocking {
        val username = "Asdrubal"
        val id = "id01"
        val challengeDetails = ChallengeDetails(playerUsername = username, challengeId = id)
        challengeDetailsDao.insertChallengeDetails(challengeDetails)
        var challengeList: List<ChallengeDetails> = challengeDetailsDao.requestChallengeDetailsBy(challengeId = id, playerUsername = username)
        Assert.assertEquals(1, challengeList.size)

        challengeDetailsDao.clearChallengeDetailsBy(challengeId = id, playerUsername = "burg")
        challengeList = challengeDetailsDao.requestChallengeDetailsBy(challengeId = id, playerUsername = username)
        Assert.assertEquals(1, challengeList.size)

        challengeDetailsDao.clearChallengeDetailsBy(challengeId = id, playerUsername = username)
        challengeList = challengeDetailsDao.requestChallengeDetailsBy(challengeId = id, playerUsername = username)
        Assert.assertEquals(0, challengeList.size)
    }

    @Test
    fun removeElementsIfNotInPlayerTable() = runBlocking {
        val username1 = "Asdrubal"
        val username2 = "Trombone"
        val id01 = "id01"
        val id02 = "id02"

        val challengeDetails1 = ChallengeDetails(playerUsername = username1, challengeId = id01)
        val challengeDetails2 = ChallengeDetails(playerUsername = username2, challengeId = id02)
        val player1 = Player(username1, 1, 0, "", 0)

        challengeDetailsDao.insertChallengeDetails(challengeDetails1)
        challengeDetailsDao.insertChallengeDetails(challengeDetails2)
        playersDao.insert(player1)

        var challengeList: List<ChallengeDetails> = challengeDetailsDao.requestChallengeDetailsBy(challengeId = id01, playerUsername = username1)
        Assert.assertEquals(1, challengeList.size)

        challengeList = challengeDetailsDao.requestChallengeDetailsBy(challengeId = id02, playerUsername = username2)
        Assert.assertEquals(1, challengeList.size)

        challengeDetailsDao.keepChallengeDetailsFromLast5Players()

        challengeList = challengeDetailsDao.requestChallengeDetailsBy(challengeId = id01, playerUsername = username1)
        Assert.assertEquals(1, challengeList.size)

        challengeList = challengeDetailsDao.requestChallengeDetailsBy(challengeId = id02, playerUsername = username2)
        Assert.assertEquals(true, challengeList.isEmpty())
    }


}