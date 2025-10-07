package com.example.myapplication.data

import com.example.myapplication.data.UpcomingBadge

data class UserBadgesData(
    val activeBadge: Any,
    val badges: List<Any>,
    val badgesCount: Int,
    val upcomingBadges: List<UpcomingBadge>
)