package com.anupdey.app.discover_github_repositories.utils.ext

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.anupdey.app.discover_github_repositories.databinding.LayoutErrorBinding
import com.anupdey.app.discover_github_repositories.utils.network.ApiError

fun Context?.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showError(binding: LayoutErrorBinding, apiError: ApiError, retry: () -> Unit) {
    binding.parent.isVisible = true
    binding.retryBtn.isEnabled = true
    binding.progressBar.isVisible = false
    binding.msg.text = apiError.message
    binding.retryBtn.setOnClickListener {
        retry.invoke()
        binding.retryBtn.isEnabled = false
        binding.progressBar.isVisible = true
    }
}

fun Fragment.hideError(binding: LayoutErrorBinding) {
    binding.parent.isVisible = false
}