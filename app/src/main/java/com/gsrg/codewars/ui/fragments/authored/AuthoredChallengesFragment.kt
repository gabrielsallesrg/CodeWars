package com.gsrg.codewars.ui.fragments.authored

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gsrg.codewars.R
import com.gsrg.codewars.databinding.FragmentAuthoredChallengesBinding

class AuthoredChallengesFragment : Fragment() {

    private lateinit var binding: FragmentAuthoredChallengesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthoredChallengesBinding.inflate(inflater, container, false)
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_authoredChallengesFragment_to_challengeDetailsFragment)
        }
        return binding.root
    }
}