package com.example.myapplication.data

import com.example.myapplication.data.Submission

data class SubmissionsData(
    val count: Int,
    val submission: List<Submission>
)
data class SubmissionResponse(
    val submission: List<Submission>,
    val count: Int
    )