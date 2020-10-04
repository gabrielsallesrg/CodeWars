package com.gsrg.codewars.ui.fragments.search

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.gsrg.codewars.database.players.Player

class SearchHistoryAdapter : PagingDataAdapter<Player, SearchHistoryItemViewHolder>(PLAYER_COMPARATOR) {

    override fun onBindViewHolder(holder: SearchHistoryItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryItemViewHolder {
        return SearchHistoryItemViewHolder.create(parent)
    }

    companion object {
        private val PLAYER_COMPARATOR = object : DiffUtil.ItemCallback<Player>() {
            override fun areItemsTheSame(oldItem: Player, newItem: Player) = oldItem.playerUserName == newItem.playerUserName
            override fun areContentsTheSame(oldItem: Player, newItem: Player) = oldItem == newItem
        }
    }
}