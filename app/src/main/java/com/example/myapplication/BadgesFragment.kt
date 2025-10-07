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
import com.example.myapplication.databinding.FragmentBadgesBinding
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class BadgesFragment : Fragment() {

    private var _binding: FragmentBadgesBinding? = null
    private val binding get() = _binding!!
    private val args: BadgesFragmentArgs by navArgs()
    private lateinit var badgesAdapter: BadgesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBadgesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        val username = args.username
        if (username.isNotEmpty()) {
            fetchBadges(username)
        } else {
            Toast.makeText(requireContext(), "Username not provided.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setupRecyclerView() {
        badgesAdapter = BadgesAdapter(emptyList())
        binding.badgesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = badgesAdapter
        }
    }

    private fun fetchBadges(username: String) {
        lifecycleScope.launch {
            binding.progressBar.isVisible = true
            binding.badgesCountCard.isVisible = false
            binding.badgesRecyclerView.isVisible = false

            try {
                val userBadgesData = RetrofitInstance.apiService.getUserBadges(username)
                binding.badgesCountTextView.text = userBadgesData.badges.size.toString()
                badgesAdapter.updateData(userBadgesData.badges)

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
                binding.badgesCountCard.isVisible = true
                binding.badgesRecyclerView.isVisible = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
