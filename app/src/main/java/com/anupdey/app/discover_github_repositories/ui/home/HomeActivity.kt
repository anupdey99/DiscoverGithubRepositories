package com.anupdey.app.discover_github_repositories.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import com.anupdey.app.discover_github_repositories.R
import com.anupdey.app.discover_github_repositories.databinding.ActivityHomeBinding
import com.anupdey.app.discover_github_repositories.utils.ext.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private var exit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_DiscoverGithubRepositories)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    override fun onBackPressed() {
        if (exit) {
            super.onBackPressed()
        } else {
            toast(getString(R.string.exit_msg))
            exit = true
            Handler(Looper.getMainLooper()).postDelayed({
                exit = false
            }, 2000L)
        }
    }

}