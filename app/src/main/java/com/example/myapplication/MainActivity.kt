package com.example.myapplication

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
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
                    if (!sharedViewModel.isInitialUserAdded) Toast.makeText(
                        this, "Please add a user first!", Toast.LENGTH_SHORT
                    ).show()
                    else {
                        sharedViewModel.isUserMenuActive = true
                        val bundle = Bundle().apply {
                            putString("username", sharedViewModel.username.value)
                        }
                        navController.navigate(R.id.profileFragment, bundle)
                    }
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.searchFriend -> {
                    if (!sharedViewModel.isInitialUserAdded) Toast.makeText(
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
                    }
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                else -> false
            }
            true
        }

        binding.bottomNavView.setOnItemSelectedListener { menuItem ->
            if (!sharedViewModel.isInitialUserAdded) Toast.makeText(
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
                navController.navigate(menuItem.itemId, bundle)
            }
            true
        }
    }

    fun setupBackButton() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Check if the drawer is open and close it if it is
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    // If the drawer is not open, perform the default back press action
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                    isEnabled = true
                }
            }
        }
        // Add the callback to the activity's dispatcher
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }
}
