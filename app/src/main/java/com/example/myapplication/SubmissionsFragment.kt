package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentSubmissionsBinding

class SubmissionsFragment : Fragment() {

    private var _binding: FragmentSubmissionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 5. Inflate the layout using View Binding
        _binding = FragmentSubmissionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sampleSubmissions = listOf(
            Submission("Java", "Accepted", "2 days ago", "Two Sum"),
            Submission("Python", "Accepted", "5 days ago", "Longest Substring Without Repeating Characters"),
        )

        setupRecyclerView(sampleSubmissions)
    }

    private fun setupRecyclerView(submissions: List<Submission>) {
        binding.submissionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = SubmissionsAdapter(submissions)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
