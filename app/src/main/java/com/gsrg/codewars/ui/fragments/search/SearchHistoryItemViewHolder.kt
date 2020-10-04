package com.gsrg.codewars.ui.fragments.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gsrg.codewars.R
import com.gsrg.codewars.database.players.Player

class SearchHistoryItemViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
    private val nameTextView: TextView = view.findViewById(R.id.nameTextView)

    init {
        view.setOnClickListener {
            //TODO
        }
    }

    fun bind(player: Player?) {
        if (player != null) {
            nameTextView.text = player.playerUserName
        }
    }

    companion object {
        fun create(parent: ViewGroup): SearchHistoryItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.search_history_item, parent, false)
            return SearchHistoryItemViewHolder(view)
        }
    }
}