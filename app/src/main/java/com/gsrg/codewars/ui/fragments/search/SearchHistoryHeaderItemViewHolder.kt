package com.gsrg.codewars.ui.fragments.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gsrg.codewars.R

class SearchHistoryHeaderItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(parent: ViewGroup): SearchHistoryHeaderItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.search_history_header_item, parent, false)
            return SearchHistoryHeaderItemViewHolder(view)
        }
    }
}