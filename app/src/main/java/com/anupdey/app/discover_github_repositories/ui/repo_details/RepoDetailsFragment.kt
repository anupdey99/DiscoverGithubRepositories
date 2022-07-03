package com.anupdey.app.discover_github_repositories.ui.repo_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.anupdey.app.discover_github_repositories.data.remote.models.RepoData
import com.anupdey.app.discover_github_repositories.databinding.FragmentRepoDetailsBinding
import com.anupdey.app.discover_github_repositories.ui.home.RepoViewModel
import com.anupdey.app.discover_github_repositories.utils.ext.convertStarCount
import com.anupdey.app.discover_github_repositories.utils.ext.formatDate
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class RepoDetailsFragment: Fragment() {

    private var _binding: FragmentRepoDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RepoViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentRepoDetailsBinding.inflate(layoutInflater, container, false).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedRepo.collectLatest { model ->
                    if (model == null) return@collectLatest
                    Timber.d("selected repo $model")
                    bindUI(model)
                }
            }
        }
    }

    private fun bindUI(model: RepoData) {
        binding.apply {
            Glide.with(avatar)
                .load(model.owner?.avatarUrl)
                .into(avatar)
            name.text = model.name
            repoName.text = model.fullName
            repoDes.text = model.description
            repoStar.text = convertStarCount(model.stargazersCount)
            repoFork.text = convertStarCount(model.forksCount)
            repoWatch.text = convertStarCount(model.watchersCount)
            repoUpdated.text = formatDate(model.updatedAt)
            repoLanguage.text = model.language ?: "-"
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}