package com.gsrg.codewars.ui.fragments.authored

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gsrg.codewars.databinding.FragmentAuthoredChallengesBinding
import com.gsrg.codewars.ui.PlayerDataViewModel
import com.gsrg.codewars.ui.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthoredChallengesFragment : BaseFragment() {

    private lateinit var binding: FragmentAuthoredChallengesBinding
    private val authoredChallengesViewModel: AuthoredChallengesViewModel by viewModels()
    private val playerDataViewModel: PlayerDataViewModel by activityViewModels()
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthoredChallengesBinding.inflate(inflater, container, false)
        setRecyclerView()
        search(playerDataViewModel.playerUsername)
        return binding.root
    }

    private fun setRecyclerView() {
        binding.authoredRecyclerView.let {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            //TODO it.adapter = adapter
        }
        //TODO adapter.addLoadStateListener
    }

    private fun search(username: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            authoredChallengesViewModel.requestAuthoredChallenges(username = username).collectLatest {
                //TODO adapter.submitData(it)
            }
        }
    }
}