package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.data.Badges
import com.example.myapplication.databinding.ItemBadgesBinding

class BadgesAdapter(private var badges: List<Badges>) :
    RecyclerView.Adapter<BadgesAdapter.BadgesViewHolder>() {

    class BadgesViewHolder(val binding: ItemBadgesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(badge: Badges) {
            binding.badgeNameTextView.text = badge.displayName

            // Use Glide to load the badge icon from its URL
            Glide.with(binding.root.context)
                .load(badge.icon)
                .placeholder(R.drawable.status_other_bg) // Optional: show a placeholder while loading
                .error(R.drawable.outline) // Optional: show an error image if loading fails
                .into(binding.badgeImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgesViewHolder {
        val binding = ItemBadgesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BadgesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BadgesViewHolder, position: Int) {
        holder.bind(badges[position])
    }

    override fun getItemCount() = badges.size

    fun updateData(newBadges: List<Badges>) {
        badges = newBadges
        notifyDataSetChanged()
    }
}
