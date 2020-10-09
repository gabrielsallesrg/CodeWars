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
class CompletedRemoteKeysDaoTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var playersDao: PlayersDao
    private lateinit var completedRemoteKeysDao: CompletedRemoteKeysDao
    private lateinit var codeWarsDatabase: CodeWarsDatabase

    @Before
    fun createDb() {
        Dispatchers.setMain(testDispatcher)
        val context = ApplicationProvider.getApplicationContext<Context>()
        codeWarsDatabase = Room.inMemoryDatabaseBuilder(
            context, CodeWarsDatabase::class.java
        ).build()
        playersDao = codeWarsDatabase.playersDao()
        completedRemoteKeysDao = codeWarsDatabase.completedRemoteKeysDao()
    }

    @After
    @Throws(IOException::class)
    fun deleteDataAndCloseDatabase() {
        codeWarsDatabase.close()
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun insertAndRemoteKeys() = runBlocking {
        val username = "Asdrubal"
        val id = "id01"
        val remoteKeys = CompletedRemoteKeys(username = username, challengeId = id, null, null)
        completedRemoteKeysDao.insertAll(listOf(remoteKeys))
        var remoteKeyRequested: CompletedRemoteKeys? = completedRemoteKeysDao.completedRemoteKeys(username = username, challengeId = id)
        Assert.assertEquals(remoteKeys.challengeId, remoteKeyRequested?.challengeId)

        completedRemoteKeysDao.clearRemoteKeysByUsername(username)
        remoteKeyRequested = completedRemoteKeysDao.completedRemoteKeys(username = username, challengeId = id)
        Assert.assertEquals(true, remoteKeyRequested == null)
    }

    @Test
    fun removeElementsIfNotInPlayerTable() = runBlocking {
        val username1 = "Asdrubal"
        val username2 = "Trombone"
        val id01 = "id01"
        val id02 = "id02"

        val remoteKeys1 = CompletedRemoteKeys(username = username1, challengeId = id01, null, null)
        val remoteKeys2 = CompletedRemoteKeys(username = username2, challengeId = id02, null, null)
        val player1 = Player(username1, 1, 0, "", 0)

        completedRemoteKeysDao.insertAll(listOf(remoteKeys1, remoteKeys2))
        playersDao.insert(player1)

        completedRemoteKeysDao.keepRemoteKeysFromLast5Players()

        var remoteKey = completedRemoteKeysDao.completedRemoteKeys(username1, id01)
        Assert.assertEquals(true, remoteKey != null)

        remoteKey = completedRemoteKeysDao.completedRemoteKeys(username2, id02)
        Assert.assertEquals(true, remoteKey == null)
    }
}