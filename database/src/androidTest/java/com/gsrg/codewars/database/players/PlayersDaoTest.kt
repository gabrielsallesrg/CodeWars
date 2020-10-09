package com.gsrg.codewars.database.players

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.gsrg.codewars.database.CodeWarsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class PlayersDaoTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var playersDao: PlayersDao
    private lateinit var codeWarsDatabase: CodeWarsDatabase

    @Before
    fun createDb() {
        Dispatchers.setMain(testDispatcher)
        val context = ApplicationProvider.getApplicationContext<Context>()
        codeWarsDatabase = Room.inMemoryDatabaseBuilder(
            context, CodeWarsDatabase::class.java
        ).build()
        playersDao = codeWarsDatabase.playersDao()
    }

    @After
    @Throws(IOException::class)
    fun deleteDataAndCloseDatabase() {
        codeWarsDatabase.close()
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun insertPlayerInDatabase() = runBlocking {
        val player = Player("AAA", 5, 0, "", 0)
        playersDao.insert(player)
        val playersList: List<Player> = playersDao.selectAllPlayers()!!
        assertThat(playersList[0], equalTo(player))
    }

    @Test
    fun insertMultiplePlayersAndKeep5() = runBlocking {
        playersDao.insert(Player("AAA", 1, 0, "", 0))
        playersDao.keepLast5Players()
        var playerList: List<Player> = playersDao.selectAllPlayers()!!
        Assert.assertEquals(1, playerList.size)

        playersDao.insert(Player("AAB", 2, 0, "", 0))
        playersDao.keepLast5Players()
        playerList = playersDao.selectAllPlayers()!!
        Assert.assertEquals(2, playerList.size)

        playersDao.insert(Player("AAC", 3, 0, "", 0))
        playersDao.keepLast5Players()
        playerList = playersDao.selectAllPlayers()!!
        Assert.assertEquals(3, playerList.size)

        playersDao.insert(Player("AAD", 4, 0, "", 0))
        playersDao.keepLast5Players()
        playerList = playersDao.selectAllPlayers()!!
        Assert.assertEquals(4, playerList.size)

        playersDao.insert(Player("AAE", 5, 0, "", 0))
        playersDao.keepLast5Players()
        playerList = playersDao.selectAllPlayers()!!
        Assert.assertEquals(5, playerList.size)

        playersDao.insert(Player("AAF", 6, 0, "", 0))
        playersDao.keepLast5Players()
        playerList = playersDao.selectAllPlayers()!!
        Assert.assertEquals(5, playerList.size)

        playersDao.insert(Player("AAG", 7, 0, "", 0))
        playersDao.keepLast5Players()
        playerList = playersDao.selectAllPlayers()!!
        Assert.assertEquals(5, playerList.size)

        playersDao.insert(Player("AAH", 8, 0, "", 0))
        playersDao.keepLast5Players()
        playerList = playersDao.selectAllPlayers()!!
        Assert.assertEquals(5, playerList.size)

        playersDao.insert(Player("AAI", 9, 0, "", 0))
        playersDao.keepLast5Players()
        playerList = playersDao.selectAllPlayers()!!
        Assert.assertEquals(5, playerList.size)

        playersDao.insert(Player("AAJ", 10, 0, "", 0))
        playersDao.keepLast5Players()
        playerList = playersDao.selectAllPlayers()!!
        Assert.assertEquals(5, playerList.size)

    }
}