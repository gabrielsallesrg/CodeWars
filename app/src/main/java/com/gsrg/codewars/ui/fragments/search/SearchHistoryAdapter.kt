package com.gsrg.codewars.ui.fragments.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gsrg.codewars.R
import com.gsrg.codewars.database.players.Player

private const val HEADER_POSITION = 0

class SearchHistoryAdapter(private val itemClickAction: (Player) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList: MutableList<Player> = mutableListOf()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position > HEADER_POSITION) {
            (holder as SearchHistoryItemViewHolder).apply {
                val player = dataList[position - 1]
                bind(player)
                itemView.setOnClickListener { itemClickAction.invoke(player) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.search_history_header_item -> SearchHistoryHeaderItemViewHolder.create(parent)
            else -> SearchHistoryItemViewHolder.create(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            HEADER_POSITION -> R.layout.search_history_header_item
            else -> R.layout.search_history_item
        }
    }

    override fun getItemCount(): Int = if (dataList.isEmpty()) 0 else dataList.count() + 1

    fun submitData(playersList: List<Player>) {
        if (playersList != dataList) {
            dataList = playersList.toMutableList()
            notifyDataSetChanged()
        }
    }
}