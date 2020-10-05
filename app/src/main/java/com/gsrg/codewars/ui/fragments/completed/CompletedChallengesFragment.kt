package com.gsrg.codewars.ui.fragments.completed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.gsrg.codewars.R
import com.gsrg.codewars.databinding.FragmentCompletedChallengesBinding
import com.gsrg.codewars.ui.PlayerDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompletedChallengesFragment : Fragment() {

    private lateinit var binding: FragmentCompletedChallengesBinding
    private val completedViewModel: CompletedChallengesViewModel by viewModels()
    private val playerDataViewModel: PlayerDataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompletedChallengesBinding.inflate(inflater, container, false)
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_completedChallengesFragment_to_challengeDetailsFragment)
        }
        setListeners()
        setObservers()
        setRecyclerView()
        return binding.root
    }

    private fun setListeners() {
        //TODO
    }

    private fun setObservers() {
        //TODO
    }

    private fun setRecyclerView() {
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.challengesRecyclerView.addItemDecoration(decoration)
        //TODO
    }
}