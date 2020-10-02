package com.gsrg.codewars.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gsrg.codewars.R
import com.gsrg.codewars.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.placeHolderButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_challengesFragment)
        }
        binding.searchTextInputLayout.setEndIconOnClickListener {

        }
    }
}