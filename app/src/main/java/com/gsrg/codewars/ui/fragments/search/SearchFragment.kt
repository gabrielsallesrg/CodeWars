package com.gsrg.codewars.ui.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gsrg.codewars.R
import com.gsrg.codewars.databinding.FragmentSearchBinding
import com.gsrg.codewars.domain.api.Result
import com.gsrg.codewars.domain.utils.TAG
import com.gsrg.codewars.ui.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private val adapter = SearchHistoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.searchViewModel = searchViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setListeners()
        setObservers()
        setRecyclerView()
        return binding.root
    }

    private fun setRecyclerView() {
        binding.last5RecyclerView.adapter = adapter
        binding.last5RecyclerView.layoutManager = LinearLayoutManager(requireContext())
        searchViewModel.retrieveSavedSearchedNames()
    }

    private fun setListeners() {
        binding.playerDetailsCardView.setOnClickListener {
            searchViewModel.saveSearchedName()
            findNavController().navigate(R.id.action_searchFragment_to_challengesFragment)
        }
        binding.searchTextInputLayout.setEndIconOnClickListener {
            binding.searchTextInputEditText.text.toString().let {
                if (it.isNotBlank()) {
                    searchViewModel.searchForUserByName(it)
                } else {
                    Toast.makeText(requireContext(), "Invalid player name", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setObservers() {
        searchViewModel.playerLiveData.observe(viewLifecycleOwner, {
            when (val result = it.getContentIfNotHandled()) {
                is Result.Success -> {
                    hideLoading()
                    Timber.tag(TAG()).d(result.data.toString())
                }
                is Result.Error -> {
                    hideLoading()
                    Timber.tag(TAG()).d(result.message)
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> showLoading()
            }
        })
        searchViewModel.last5playersLiveData.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                adapter.submitData(it)
                binding.last5RecyclerView.isVisible = true
            }
        })
    }
}