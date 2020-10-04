package com.gsrg.codewars.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gsrg.codewars.databinding.ActivityMainBinding
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
}