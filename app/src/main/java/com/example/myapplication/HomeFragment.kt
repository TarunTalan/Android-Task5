package com.example.myapplication

// Import NavOptions for navigation
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navOptions =
            NavOptions.Builder().setLaunchSingleTop(true) // Don't add to stack if already at top
                .build()
        if (sharedViewModel.isInitialUserAdded.value == true) {
            binding.addUserButton.isVisible = false
            binding.searchFriendButton.isVisible = true
            binding.searchFriendButton.isEnabled = false
        } else {
            binding.addUserButton.isVisible = true
            binding.addUserButton.isEnabled = false
            binding.searchFriendButton.isVisible = false
        }

        binding.usernameEditText.doOnTextChanged { text, _, _, _ ->
            val isTextNotEmpty = !text.isNullOrEmpty()
            binding.addUserButton.isEnabled = isTextNotEmpty
            binding.searchFriendButton.isEnabled = isTextNotEmpty
        }

        binding.addUserButton.setOnClickListener {
            sharedViewModel.isUserMenuActive = true
            val username = binding.usernameEditText.text.toString()
            if (username.isNotEmpty()) {
                sharedViewModel.isInitialUserAdded.value = true
                sharedViewModel.username.value = username
                Toast.makeText(requireContext(), "User '$username' added!", Toast.LENGTH_SHORT)
                    .show()
                binding.usernameEditText.text.clear()
                val bundle = Bundle().apply {
                    putString("username", username)
                }

                findNavController().navigate(
                    R.id.action_homeFragment_to_profileFragment,
                    bundle,
                    navOptions
                )
            }
        }

        binding.searchFriendButton.setOnClickListener {
            sharedViewModel.isUserMenuActive = false
            val friendUsername = binding.usernameEditText.text.toString()
            if (friendUsername.isNotEmpty()) {
                sharedViewModel.issearchingFriend = true
                sharedViewModel.friendName.value = friendUsername
                Toast.makeText(
                    requireContext(), "Searching for '$friendUsername'...", Toast.LENGTH_SHORT
                ).show()
                binding.usernameEditText.text.clear()
                val bundle = Bundle().apply {
                    putString("username", friendUsername)
                }
                findNavController().navigate(
                    R.id.action_homeFragment_to_profileFragment,
                    bundle,
                    navOptions
                )
                binding.usernameEditText.text.clear()
            } else {
                Toast.makeText(
                    requireContext(), "Please enter a friend's username", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
