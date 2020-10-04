package com.gsrg.codewars.ui.fragments

import androidx.fragment.app.Fragment
import com.gsrg.codewars.ui.MainActivity

abstract class BaseFragment : Fragment() {

    fun showLoading() = (requireActivity() as MainActivity).showLoading()
    fun hideLoading() = (requireActivity() as MainActivity).hideLoading()
}