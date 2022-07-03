package com.anupdey.app.discover_github_repositories.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anupdey.app.discover_github_repositories.data.remote.models.RepoData
import com.anupdey.app.discover_github_repositories.data.repository.GithubRepository
import com.anupdey.app.discover_github_repositories.ui.top_repo.ViewState
import com.anupdey.app.discover_github_repositories.utils.AppConstant
import com.anupdey.app.discover_github_repositories.utils.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    private val repoList: MutableList<RepoData> = mutableListOf()
    private val viewStateChannel = Channel<ViewState>()
    val viewStateEvent = viewStateChannel.receiveAsFlow()

    private val _selectedRepo = MutableStateFlow<RepoData?>(null)
    val selectedRepo: StateFlow<RepoData?> = _selectedRepo

    init {
        fetchTopRepos()
    }

    fun fetchTopRepos() {
        viewModelScope.launch {
            val response = repository.fetchTopRepos(
                "Android",
                AppConstant.GITHUB_SEARCH_SORT,
                AppConstant.GITHUB_SEARCH_PER_PAGE,
                AppConstant.GITHUB_SEARCH_PAGE
            )
            response.collectLatest { resource ->
                when (resource) {
                    is Resource.Error -> {
                        Timber.d("fetchTopRepos ${resource.error?.message}")
                        viewStateChannel.send(ViewState.ProgressState(false))
                        viewStateChannel.send(ViewState.ShowError(resource.error!!))
                    }
                    is Resource.Loading -> {
                        Timber.d("fetchTopRepos loading")
                        viewStateChannel.send(ViewState.ProgressState(true))
                    }
                    is Resource.Success -> {
                        Timber.d("fetchTopRepos ${resource.data}")
                        repoList.clear()
                        repoList.addAll(resource.data!!)
                        viewStateChannel.send(ViewState.ProgressState(false))
                        viewStateChannel.send(ViewState.InitData(repoList))
                    }
                }
            }
        }
    }

    fun retry() {
        fetchTopRepos()
    }

    fun sortByStar() {
        viewModelScope.launch {
            repoList.sortByDescending { it.stargazersCount }
            viewStateChannel.send(ViewState.InitData(repoList))
        }
    }

    fun sortByUpdateDate() {
        viewModelScope.launch {
            repoList.sortByDescending { it.updatedAt }
            viewStateChannel.send(ViewState.InitData(repoList))
        }
    }

    fun selectRepo(model: RepoData) {
        _selectedRepo.value = model
    }
}