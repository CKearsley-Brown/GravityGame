package com.ckb.gravitygame

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ckb.gravitygame.databinding.LayoutItemViewBinding

class LeaderboardAdapter(val leaderboard: ArrayList<Result>) :  RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>()  {

    inner class ViewHolder(var itemViewBinding: LayoutItemViewBinding) : RecyclerView.ViewHolder(itemViewBinding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardAdapter.ViewHolder {
        val itemViewBinding = LayoutItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: LeaderboardAdapter.ViewHolder, position: Int) {
        val leaderboardItem = leaderboard[position]
        holder.itemViewBinding.nameDisplay.text = "Name: " + leaderboardItem.name
        holder.itemViewBinding.scoreDisplay.text = "Score: " + leaderboardItem.score
    }

    override fun getItemCount(): Int {
        return leaderboard.size
    }
}