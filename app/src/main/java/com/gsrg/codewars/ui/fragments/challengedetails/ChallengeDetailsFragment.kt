package com.gsrg.codewars.ui.fragments.challengedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.gsrg.codewars.databinding.FragmentChallengeDetailsBinding
import com.gsrg.codewars.domain.api.Result
import com.gsrg.codewars.domain.utils.TAG
import com.gsrg.codewars.ui.PlayerDataViewModel
import com.gsrg.codewars.ui.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ChallengeDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentChallengeDetailsBinding
    private val challengeDetailsViewModel: ChallengeDetailsViewModel by viewModels()
    private val playerDataViewModel: PlayerDataViewModel by activityViewModels()
    private val args: ChallengeDetailsFragmentArgs by navArgs()
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChallengeDetailsBinding.inflate(inflater, container, false)
        binding.viewModel = challengeDetailsViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setObservers()
        search(username = playerDataViewModel.playerUsername, challengeId = args.challengeId)
        return binding.root
    }

    private fun setObservers() {
        challengeDetailsViewModel.challengeRequestLiveData.observe(viewLifecycleOwner, {
            when (val result = it.getContentIfNotHandled()) {
                is Result.Success -> hideLoading()
                is Result.Error -> {
                    hideLoading()
                    Timber.tag(TAG()).d(result.message)
                    showMessage(binding.root, result.message)
                }
                is Result.Loading -> showLoading()
            }
        })
    }

    private fun search(username: String, challengeId: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            challengeDetailsViewModel.requestChallengeDetails(username = username, challengeId = challengeId)
        }
    }
}