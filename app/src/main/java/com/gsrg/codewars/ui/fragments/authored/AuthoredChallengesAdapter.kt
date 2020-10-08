package com.gsrg.codewars.ui.fragments.authored

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.gsrg.codewars.database.challenges.AuthoredChallenge
import com.gsrg.codewars.ui.fragments.completed.ChallengeViewHolder

class AuthoredChallengesAdapter(
    private val itemClickAction: (AuthoredChallenge) -> Unit
) : PagingDataAdapter<AuthoredChallenge, ChallengeViewHolder>(CHALLENGE_COMPARATOR) {

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        getItem(position)?.let { challenge: AuthoredChallenge ->
            holder.bind(challenge.challengeName ?: "Null")
            holder.itemView.setOnClickListener { itemClickAction.invoke(challenge) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        return ChallengeViewHolder.create(parent)
    }

    companion object {
        private val CHALLENGE_COMPARATOR = object : DiffUtil.ItemCallback<AuthoredChallenge>() {
            override fun areItemsTheSame(oldItem: AuthoredChallenge, newItem: AuthoredChallenge): Boolean {
                return oldItem.challengeId == newItem.challengeId && oldItem.username == newItem.username
            }

            override fun areContentsTheSame(oldItem: AuthoredChallenge, newItem: AuthoredChallenge): Boolean {
                return oldItem == newItem
            }
        }
    }
}