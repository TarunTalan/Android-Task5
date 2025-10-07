package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.profileButton.setOnClickListener {
            val username = binding.usernameTextView.text.toString().trim()

            if (username.isNotEmpty()) {
                val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment(username)
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Please enter a username", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.submissionsButton.setOnClickListener {
            val username = binding.usernameTextView.text.toString().trim()
            if (username.isNotEmpty()) {
                val action = HomeFragmentDirections.actionHomeFragmentToSubmissionsFragment(username)
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Please enter a username", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        binding.solvedButton.setOnClickListener {
            val username = binding.usernameTextView.text.toString().trim()
            if (username.isNotEmpty()) {
                val action = HomeFragmentDirections.actionHomeFragmentToSolvedFragment(username)
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Please enter a username", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        binding.badgesButton.setOnClickListener {
            val username = binding.usernameTextView.text.toString().trim()
            if (username.isNotEmpty()) {
                val action = HomeFragmentDirections.actionHomeFragmentToBadgesFragment(username)
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Please enter a username", Toast.LENGTH_SHORT)
                    .show()
            }

        }

    }
}
