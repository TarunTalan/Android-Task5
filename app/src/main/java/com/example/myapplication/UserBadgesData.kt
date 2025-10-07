package com.example.myapplication

data class UserBadgesData(
    val activeBadge: Any,
    val badges: List<Any>,
    val badgesCount: Int,
    val upcomingBadges: List<UpcomingBadge>
)