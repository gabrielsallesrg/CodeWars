package com.gsrg.codewars.ui.fragments.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gsrg.codewars.database.CodeWarsDatabaseMock
import com.gsrg.codewars.database.players.Player
import com.gsrg.codewars.domain.data.player.PlayerRepositoryMock
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {
    @get:Rule
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: SearchViewModel

    @Before
    fun init() {
        viewModel = SearchViewModel(
            playerRepository = PlayerRepositoryMock(),
            database = CodeWarsDatabaseMock()
        )
    }

    @Test
    fun sortList() {
        val player1 = Player(playerUserName = "01", date = 20, rank = 5, bestLanguage = "", bestLanguageScore = 0)
        val player2 = Player(playerUserName = "02", date = 10, rank = 8, bestLanguage = "", bestLanguageScore = 0)
        var list = listOf(player1, player2)
        list = viewModel.sort(list)
        assertEquals(player1.playerUserName, list[0].playerUserName)

        viewModel.sortByRank = true
        list = viewModel.sort(list)
        assertEquals(player2.playerUserName, list[0].playerUserName)
    }
}