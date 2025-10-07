package com.example.myapplication

data class SubmissionsData(
    val count: Int,
    val submission: List<Submission>
)
data class SubmissionResponse(
    val submission: List<Submission>,
    val count: Int
    )