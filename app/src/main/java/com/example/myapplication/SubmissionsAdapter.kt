package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemSubmissionsBinding

class SubmissionsAdapter(private val submissions: List<Submission>) :
    RecyclerView.Adapter<SubmissionsAdapter.SubmissionViewHolder>() {

    class SubmissionViewHolder(val binding: ItemSubmissionsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(submission: Submission) {
            binding.submissionTitleTextView.text = submission.title
            binding.submissionStatusTextView.text = submission.statusDisplay
            binding.languageTextView.text = submission.lang
            binding.timestampTextView.text = submission.timestamp

            val context = binding.root.context
            val statusBackground = when (submission.statusDisplay) {
                "Accepted" -> R.drawable.status_accepted_bg
                else -> R.drawable.status_other_bg
            }
            binding.submissionStatusTextView.setBackgroundResource(statusBackground)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubmissionViewHolder {
        val binding = ItemSubmissionsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SubmissionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubmissionViewHolder, position: Int) {
        val submission = submissions[position]
        holder.bind(submission)
    }


    override fun getItemCount() = submissions.size
}
