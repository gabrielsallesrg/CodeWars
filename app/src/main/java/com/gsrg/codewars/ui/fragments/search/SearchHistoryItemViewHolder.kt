package com.gsrg.codewars.ui.fragments.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gsrg.codewars.R
import com.gsrg.codewars.database.players.Player

class SearchHistoryItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var nameTextView: TextView = view.findViewById(R.id.nameTextView)
    private var rankTextView: TextView = view.findViewById(R.id.rankValueTextView)
    private var languageTextView: TextView = view.findViewById(R.id.languageValueTextView)
    private var scoreTextView: TextView = view.findViewById(R.id.scoreValueTextView)

    fun bind(player: Player?) {
        player?.let {
            nameTextView.text = it.playerUserName
            rankTextView.text = it.rank.toString()
            languageTextView.text = it.bestLanguage
            scoreTextView.text = it.bestLanguageScore.toString()
        }
    }

    companion object {
        fun create(parent: ViewGroup): SearchHistoryItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.search_history_item, parent, false)
            return SearchHistoryItemViewHolder(view)
        }
    }
}