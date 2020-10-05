package com.gsrg.codewars.ui.fragments.completed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gsrg.codewars.R

class ChallengeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val challengeNameTextView: TextView = view.findViewById(R.id.nameTextView)

    fun bind(challengeName: String) {
        challengeNameTextView.text = challengeName
    }

    companion object {
        fun create(parent: ViewGroup): ChallengeViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_challenges_item, parent, false)
            return ChallengeViewHolder(view)
        }
    }
}