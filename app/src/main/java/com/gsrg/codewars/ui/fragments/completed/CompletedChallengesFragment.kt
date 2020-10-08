package com.gsrg.codewars.ui.fragments.completed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.gsrg.codewars.database.challenges.ChallengeCompleted
import com.gsrg.codewars.databinding.FragmentCompletedChallengesBinding
import com.gsrg.codewars.ui.PlayerDataViewModel
import com.gsrg.codewars.ui.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.net.UnknownHostException

@AndroidEntryPoint
class CompletedChallengesFragment : BaseFragment() {

    private lateinit var binding: FragmentCompletedChallengesBinding
    private val completedViewModel: CompletedChallengesViewModel by viewModels()
    private val playerDataViewModel: PlayerDataViewModel by activityViewModels()

    private val adapter = CompletedChallengesAdapter(fun(challenge: ChallengeCompleted) {
        val action = CompletedChallengesFragmentDirections.actionCompletedChallengesFragmentToChallengeDetailsFragment(challengeId = challenge.challengeId)
        findNavController().navigate(action)
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompletedChallengesBinding.inflate(inflater, container, false)
        setRecyclerView()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setObservables()
        completedViewModel.requestCompletedChallengeListResult(playerDataViewModel.playerUsername)
    }

    private fun setObservables() {
        completedViewModel.challengeListLiveData.observe(viewLifecycleOwner, {
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitData(it)
            }
        })
    }

    private fun setRecyclerView() {
        binding.challengesRecyclerView.let {
            it.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            it.adapter = adapter
        }
        adapter.addLoadStateListener { loadState: CombinedLoadStates ->
            when (loadState.source.refresh) {
                is LoadState.NotLoading -> {
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