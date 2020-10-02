package com.gsrg.codewars.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gsrg.codewars.R
import com.gsrg.codewars.databinding.FragmentCompletedChallengesBinding

class CompletedChallengesFragment : Fragment() {

    private lateinit var binding: FragmentCompletedChallengesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompletedChallengesBinding.inflate(inflater, container, false)
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_completedChallengesFragment_to_challengeDetailsFragment)
        }
        return binding.root
    }
}