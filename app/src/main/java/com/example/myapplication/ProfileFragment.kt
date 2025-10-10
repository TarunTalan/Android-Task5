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
import com.example.myapplication.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val args: ProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameToShow = args.username
        if (usernameToShow.isNotEmpty()) {
            fetchProfile(usernameToShow)
        } else {
            Toast.makeText(requireContext(), "Username not provided", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchProfile(username: String) {
        lifecycleScope.launch {
            binding.progressBar.isVisible = true
            binding.profileContentGroup.isVisible = false

            try {
                val profile = RetrofitInstance.apiService.getUserProfile(username)
                binding.nameTextView.text = profile.name
                binding.rankingTextView.text = "Rank: ${profile.ranking ?: "N/A"}"
                binding.reputationTextView.text =
                    "Reputation: ${profile.reputation?.toString() ?: "N/A"}"
                binding.countryTextView.text = profile.country
                binding.aboutTextView.text = profile.about
                binding.dobTextView.text = "DOB: ${profile.birthday}"
                binding.githubTextView.text = "GitHub: ${profile.gitHub}"
                Glide.with(this@ProfileFragment).load(profile.avatar).circleCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background).into(binding.avatarImageView)


            } catch (e: HttpException) {
                Toast.makeText(requireContext(), "Error: ${e.message()}", Toast.LENGTH_LONG).show()
            } catch (e: IOException) {
                Toast.makeText(
                    requireContext(),
                    "Network error, please check your connection.",
                    Toast.LENGTH_LONG
                ).show()
            } finally {
                binding.progressBar.isVisible = false
                binding.profileContentGroup.isVisible = true
            }
        }
    }
}
