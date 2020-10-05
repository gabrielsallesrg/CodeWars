package com.gsrg.codewars.ui.fragments.completed

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.gsrg.codewars.database.challenges.ChallengeCompleted

class CompletedChallengesAdapter(private val itemClickAction: (ChallengeCompleted) -> Unit) : PagingDataAdapter<ChallengeCompleted, ChallengeViewHolder>(CHALLENGE_COMPARATOR) {

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        getItem(position)?.let { challenge: ChallengeCompleted ->
            holder.bind(challenge.challengeName ?: "Null")
            holder.itemView.setOnClickListener { itemClickAction.invoke(challenge) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        return ChallengeViewHolder.create(parent)
    }

    companion object {
        private val CHALLENGE_COMPARATOR = object : DiffUtil.ItemCallback<ChallengeCompleted>() {
            override fun areItemsTheSame(oldItem: ChallengeCompleted, newItem: ChallengeCompleted): Boolean {
                return oldItem.challengeId == newItem.challengeId && oldItem.username == newItem.username
            }

            override fun areContentsTheSame(oldItem: ChallengeCompleted, newItem: ChallengeCompleted): Boolean = oldItem == newItem
        }
    }
}