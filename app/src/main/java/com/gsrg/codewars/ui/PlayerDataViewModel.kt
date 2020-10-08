package com.gsrg.codewars.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

class PlayerDataViewModel
@ViewModelInject constructor() : ViewModel() {

    var playerUsername: String = ""
}