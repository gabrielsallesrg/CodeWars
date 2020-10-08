package com.gsrg.codewars.ui.fragments.authored

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.gsrg.codewars.database.challenges.AuthoredChallenge
import com.gsrg.codewars.databinding.FragmentAuthoredChallengesBinding
import com.gsrg.codewars.ui.PlayerDataViewModel
import com.gsrg.codewars.ui.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.net.UnknownHostException

@AndroidEntryPoint
class AuthoredChallengesFragment : BaseFragment() {

    private lateinit var binding: FragmentAuthoredChallengesBinding
    private val authoredChallengesViewModel: AuthoredChallengesViewModel by viewModels()
    private val playerDataViewModel: PlayerDataViewModel by activityViewModels()

    private val adapter = AuthoredChallengesAdapter(fun(challenge: AuthoredChallenge) {
        val action = AuthoredChallengesFragmentDirections.actionAuthoredChallengesFragmentToChallengeDetailsFragment(challengeId = challenge.challengeId)
        findNavController().navigate(action)
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthoredChallengesBinding.inflate(inflater, container, false)
        setRecyclerView()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setObservables()
        authoredChallengesViewModel.requestAuthoredChallenges(playerDataViewModel.playerUsername)
    }

    private fun setObservables() {
        authoredChallengesViewModel.authoredListLiveData.observe(viewLifecycleOwner, {
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitData(it)
            }
        })
    }

    private fun setRecyclerView() {
        binding.authoredRecyclerView.let {
            it.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            it.adapter = adapter
        }
        adapter.addLoadStateListener { loadState: CombinedLoadStates ->
            when (loadState.source.refresh) {
                is LoadState.NotLoading -> {
                    binding.authoredRecyclerView.isVisible = true
                    hideLoading()
                }
                is LoadState.Loading -> showLoading()
                is LoadState.Error -> {
                    hideLoading()
                }
            }
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
                ?: loadState.refresh as? LoadState.Error

            if (errorState != null) {
                val message = if (errorState.error is UnknownHostException) "Something went wrong. Check your internet connection." else "${errorState.error}"
                showMessage(binding.root, message)
            }

        }
    }
}