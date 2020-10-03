package com.gsrg.codewars.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gsrg.codewars.R
import com.gsrg.codewars.databinding.FragmentChallengesBinding
import com.gsrg.codewars.ui.MainActivity
import com.gsrg.codewars.util.setupWithNavController

class ChallengesFragment : Fragment() {

    private lateinit var binding: FragmentChallengesBinding
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChallengesBinding.inflate(inflater, container, false)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setupBottomNavigationBar()
    }

    override fun onResume() {
        super.onResume()
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val bottomNavigationView = binding.bottomNav
        val navGraphIds = listOf(
            R.navigation.completed_challenges_navigation,
            R.navigation.authored_challenges_navigation
        )

        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = childFragmentManager,
            containerId = R.id.nav_host_container,
            intent = requireActivity().intent
        )

        controller.observe(viewLifecycleOwner, { navController: NavController ->
            (requireActivity() as MainActivity).setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> currentNavController?.value?.navigateUp() ?: false
            else -> super.onOptionsItemSelected(item)
        }
    }

}