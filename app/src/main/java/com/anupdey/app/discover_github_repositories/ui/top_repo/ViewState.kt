package com.anupdey.app.discover_github_repositories.ui.top_repo

import com.anupdey.app.discover_github_repositories.data.remote.models.RepoData
import com.anupdey.app.discover_github_repositories.utils.network.ApiError

sealed class ViewState {
    data class ShowError(val error: ApiError) : ViewState()
    data class ProgressState(val isShow: Boolean = false, val type: Int = 0) : ViewState()
    data class EmptyState(val isShow: Boolean = false, val type: Int = 0) : ViewState()
    data class InitData(val list: List<RepoData>): ViewState()
}
