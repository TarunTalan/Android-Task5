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
import com.bumptech.glide.Glide
import com.example.myapplication.api.RetrofitInstance
import com.example.myapplication.databinding.FragmentSolvedBinding
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class SolvedFragment : Fragment() {
    private lateinit var binding: FragmentSolvedBinding
    private val args: SolvedFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSolvedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameToShow = args.username
        if (usernameToShow.isNotEmpty()) {
            fetchSolved(usernameToShow)
        } else {
            Toast.makeText(requireContext(), "Username not provided", Toast.LENGTH_SHORT).show()
        }
    }
    private fun fetchSolved(username: String) {
        lifecycleScope.launch {
            binding.progressBar.isVisible = true
            binding.solvedStatsCard.isVisible = false

            try {
                val solved = RetrofitInstance.apiService.getUserSolvedStats(username)
                binding.easySolvedTextView.text = solved.easySolved.toString()
                binding.mediumSolvedTextView.text = solved.mediumSolved.toString()
                binding.hardSolvedTextView.text = solved.hardSolved.toString()
                binding.solvedProblemsTitle.text = "Solved Problems: ${solved.solvedProblem}"


            } catch (e: HttpException) {
                Toast.makeText(requireContext(), "Error: ${e.message()}", Toast.LENGTH_LONG).show()
            } catch (e: IOException) {
                Toast.makeText(requireContext(), "Network error, please check your connection.", Toast.LENGTH_LONG).show()
            } finally {
                binding.progressBar.isVisible = false
                binding.solvedStatsCard.isVisible = true
            }
        }
    }
}
