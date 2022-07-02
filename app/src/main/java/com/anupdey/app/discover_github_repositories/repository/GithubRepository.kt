package com.anupdey.app.discover_github_repositories.repository

import com.anupdey.app.discover_github_repositories.data.remote.models.RepoData
import com.anupdey.app.discover_github_repositories.utils.network.Resource
import kotlinx.coroutines.flow.Flow

interface GithubRepository {

    fun fetchTopRepos(
        searchKey: String,
        sort: String,
        perPage: Int,
        page: Int,
    ): Flow<Resource<List<RepoData>>>
}