package com.gsrg.codewars.ui.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gsrg.codewars.R
import com.gsrg.codewars.databinding.FragmentSearchBinding
import com.gsrg.codewars.domain.api.Result
import com.gsrg.codewars.domain.model.PlayerResponse
import com.gsrg.codewars.domain.utils.TAG
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        setListeners()
        setObservers()
        return binding.root
    }

    private fun setListeners() {
        binding.placeHolderButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_challengesFragment)
        }
        binding.searchTextInputLayout.setEndIconOnClickListener {
            binding.searchTextInputEditText.text.toString().let {
                if (it.isNotBlank()) {
                    searchViewModel.searchForUserByName(it)
                } else {
                    Toast.makeText(requireContext(), "Invalid player name", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun setObservers() {
        searchViewModel.playerLiveData.observe(viewLifecycleOwner, Observer {
            when (val result = it.getContentIfNotHandled()) {
                is Result.Success -> {
                    Timber.tag(TAG()).d(result.data.toString())
                    result.data.let { player: PlayerResponse ->
                        binding.nameValueTextView.text =
                            if (player.name.isNullOrBlank()) player.username else player.name
                    }
                }
                is Result.Error -> {
                    Timber.tag(TAG()).d(result.message)
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    Timber.tag(TAG()).d("Loading")
                }
            }
        })
    }
}