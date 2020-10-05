package com.gsrg.codewars.ui.fragments.challengedetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.gsrg.codewars.database.CodeWarsDatabase
import com.gsrg.codewars.domain.data.challengedetails.IChallengeDetailsRepository

class ChallengeDetailsViewModel
@ViewModelInject constructor(
    private val challengeDetailsRepository: IChallengeDetailsRepository,
    private val database: CodeWarsDatabase
) : ViewModel() {

    fun requestChallengeDetails(username: String, challengeId: String) {

    }
}