package com.example.myapplication.data

import com.example.myapplication.data.TotalSubmissionNum

data class SolvedData(
    val acSubmissionNum: List<AcSubmissionNum>,
    val easySolved: Int,
    val hardSolved: Int,
    val mediumSolved: Int,
    val solvedProblem: Int,
    val totalSubmissionNum: List<TotalSubmissionNum>
)