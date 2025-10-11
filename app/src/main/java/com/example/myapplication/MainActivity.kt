package com.example.myapplication

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        setupMenuUsernameObserver()
        setupDrawerAndBottomNav()
        setupBackButton()
        setupBottomNavVisibilityObserver()
    }

    private fun setupBottomNavVisibilityObserver() {
        sharedViewModel.isInitialUserAdded.observe(
            this,
            androidx.lifecycle.Observer { isUserAdded: Boolean ->
                if (isUserAdded == true) {
                    showBottomNav()
                } else {
                    hideBottomNav()
                }
            })
    }


    private fun showBottomNav() {
        val bottomNavView = binding.bottomNavView
        if (bottomNavView.visibility == View.VISIBLE) return

        bottomNavView.apply {
            alpha = 0f // Start transparent
            visibility = View.VISIBLE
            animate().alpha(1f) // Fade in
                .setDuration(200) // Animation duration
                .setListener(null)
        }
    }

    private fun hideBottomNav() {
        val bottomNavView = binding.bottomNavView
        if (bottomNavView.visibility == View.GONE) return

        bottomNavView.animate().alpha(0f).setDuration(30)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    bottomNavView.visibility = View.GONE
                }
            })
    }

    fun setupNavigation() {
        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        drawerLayout = binding.drawerLayout
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val menuItemId = when (destination.id) {
                R.id.homeFragment -> R.id.profileFragment
                else -> destination.id
            }
            binding.bottomNavView.menu.findItem(menuItemId)?.isChecked = true
        }
    }

    private fun setupMenuUsernameObserver() {
        val userProfileMenuItem: MenuItem? = binding.drawerNavView.menu.findItem(R.id.userName)

        // Observe the 'username' LiveData from the SharedViewModel.
        sharedViewModel.username.observe(this) { newUsername ->
            // This block of code will run every time the username changes.
            if (!newUsername.isNullOrBlank()) {
                // If we have a new valid username, set it as the title.
                userProfileMenuItem?.title = newUsername
            } else {
                // Otherwise, set it to a default value from strings.xml.
                userProfileMenuItem?.title =
                    getString(R.string.User) // Assumes you have a string resource named "User"
            }
        }
    }

    fun setupDrawerAndBottomNav() {
        binding.drawerNavView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.userName -> {
                    showBottomNav()
                    if (sharedViewModel.isInitialUserAdded.value == false) Toast.makeText(
                        this, "Please add a user first!", Toast.LENGTH_SHORT
                    ).show()
                    else {
                        val navOptions = NavOptions.Builder()
                            .setLaunchSingleTop(true) // Don't add to stack if already at top
                            .build()
                        sharedViewModel.isUserMenuActive = true
                        val bundle = Bundle().apply {
                            putString("username", sharedViewModel.username.value)
                        }
                        navController.navigate(R.id.profileFragment, bundle, navOptions)
                    }
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.searchFriend -> {
                    hideBottomNav()
                    if (sharedViewModel.isInitialUserAdded.value == false) Toast.makeText(
                        this, "Please add a user first!", Toast.LENGTH_SHORT
                    ).show()
                    else {
                        !sharedViewModel.isUserMenuActive
                        sharedViewModel.issearchingFriend = true
                        // Use NavOptions to clear the back stack before navigating
                        val navOptions = NavOptions.Builder()
                            // Pop everything up to the very start of the graph
                            .setPopUpTo(navController.graph.startDestinationId, true)
                            // Avoid creating a new HomeFragment if we're already there
                            .setLaunchSingleTop(true).build()

                        navController.navigate(R.id.homeFragment, null, navOptions)
                        binding.bottomNavView.menu.findItem(R.id.profileFragment)?.isChecked = true
                    }
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                else -> false
            }
            true
        }

        binding.bottomNavView.setOnItemSelectedListener { menuItem ->
            if (sharedViewModel.isInitialUserAdded.value == false) Toast.makeText(
                this, "Please add a user first!", Toast.LENGTH_SHORT
            ).show()
            else {

                var bundle: Bundle = if (sharedViewModel.isUserMenuActive) {
                    Bundle().apply {
                        putString("username", sharedViewModel.username.value)
                    }
                } else {
                    Bundle().apply {
                        putString("username", sharedViewModel.friendName.value)
                    }
                }
                val navOptions = NavOptions.Builder()
                    .setLaunchSingleTop(true) // Don't add to stack if already at top
                    .build()
                navController.navigate(menuItem.itemId, bundle, navOptions)
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            true
        }
    }


    private fun setupBackButton() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    // The NavController's back stack includes the start destination.
                    // A size of 2 means there is one screen on top of the start destination.
                    // We handle the default back press if there's somewhere to go back to.
                    if (navController.currentBackStack.value.size > 2) {
                        // If there are more than 2 entries (start + one more screen), navigate back.
                        navController.popBackStack()
                    } else {
                        // Otherwise, we are on the last screen, so confirm exit.
                        showExitConfirmationDialog()
                    }
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this).setTitle("Confirm Exit")
            .setMessage("Are you sure you want to exit the app?").setPositiveButton("Yes") { _, _ ->
                // If "Yes" is clicked, finish the activity to exit the app.
                finish()
            }.setNegativeButton("No", null) // If "No" is clicked, do nothing.
            .show()
    }
}
