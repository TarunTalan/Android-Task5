package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.api.RetrofitInstance
import com.example.myapplication.databinding.FragmentSubmissionsBinding
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class SubmissionsFragment : Fragment() {

    private var _binding: FragmentSubmissionsBinding? = null
    private val binding get() = _binding!!
    private val args: SubmissionsFragmentArgs by navArgs()
    private lateinit var submissionsAdapter: SubmissionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubmissionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        val username = args.username
        if (username.isNotEmpty()) {
            fetchSubmissions(username)
        } else {
            Toast.makeText(requireContext(), "Username not provided.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setupRecyclerView() {
        submissionsAdapter = SubmissionsAdapter(emptyList())
        binding.submissionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = submissionsAdapter
        }
    }

    private fun fetchSubmissions(username: String) {
        lifecycleScope.launch {
            binding.progressBar.isVisible = true
            binding.submissionsRecyclerView.isVisible = false

            try {
                val response = RetrofitInstance.apiService.getUserSubmissions(username)
                val submissionsList = response.submission
                submissionsAdapter.updateData(submissionsList)

            } catch (e: HttpException) {
                Toast.makeText(requireContext(), "Error: ${e.message()}", Toast.LENGTH_LONG).show()
            } catch (e: IOException) {
                Toast.makeText(
                    requireContext(),
                    "Network error, check your connection.",
                    Toast.LENGTH_LONG
                ).show()
            } finally {
                binding.progressBar.isVisible = false
                binding.submissionsRecyclerView.isVisible = true
            }
        }
    }

    // 8. Move onDestroyView() outside of the fetchSubmissions function
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
