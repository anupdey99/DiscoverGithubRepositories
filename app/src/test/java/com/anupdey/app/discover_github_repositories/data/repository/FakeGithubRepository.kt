package com.anupdey.app.discover_github_repositories.data.repository

import com.anupdey.app.discover_github_repositories.data.remote.endpoints.FakeGithubAPI
import com.anupdey.app.discover_github_repositories.data.remote.models.RepoData
import com.anupdey.app.discover_github_repositories.utils.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGithubRepository(
    private val fakeApi: FakeGithubAPI
) : GithubRepository {

    override fun fetchTopRepos(searchKey: String, sort: String, perPage: Int, page: Int): Flow<Resource<List<RepoData>>> {
        return flow {
            val data = fakeApi.fetchTopRepos(
                searchKey,
                sort,
                perPage,
                page
            )
            emit(Resource.Success(data.items))
        }
    }

}