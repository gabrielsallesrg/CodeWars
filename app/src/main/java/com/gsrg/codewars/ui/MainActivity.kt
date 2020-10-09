package com.gsrg.codewars.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gsrg.codewars.databinding.ActivityMainBinding
import com.gsrg.codewars.ui.fragments.ChallengesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun showLoading() {
        binding.loadingGroup.visibility = View.VISIBLE
        binding.loadingProgressBar.animate()
    }

    fun hideLoading() {
        binding.loadingGroup.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        val fragment: Fragment? = supportFragmentManager.fragments.firstOrNull()?.childFragmentManager?.fragments?.firstOrNull()
        return if (fragment is ChallengesFragment) {
            fragment.navigateUp()
        } else {
            super.onSupportNavigateUp()
        }
    }
}