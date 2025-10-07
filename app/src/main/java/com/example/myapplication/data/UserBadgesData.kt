package com.example.myapplication.data

import com.example.myapplication.data.UpcomingBadge

data class UserBadgesData(
    val activeBadge: Any,
    val badges: List<Badges>,
    val badgesCount: Int,
    val upcomingBadges: List<UpcomingBadge>
)