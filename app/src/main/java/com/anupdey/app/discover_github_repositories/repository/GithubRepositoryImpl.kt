package com.anupdey.app.discover_github_repositories.repository

import com.anupdey.app.discover_github_repositories.data.remote.endpoints.GithubAPI
import com.anupdey.app.discover_github_repositories.data.remote.models.RepoData
import com.anupdey.app.discover_github_repositories.utils.network.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepositoryImpl @Inject constructor(
    private val api: GithubAPI
): GithubRepository {

    override fun fetchTopRepos(searchKey: String, sort: String, perPage: Int, page: Int): Flow<Resource<List<RepoData>>> {
        return flow {
            emit(Resource.Loading(null))
            when (val response = apiCall { api.fetchTopRepos(searchKey, sort, perPage, page) }) {
                is ApiResponse.Error -> {
                    emit(Resource.Error(response.error!!))
                }
                is ApiResponse.Success -> {
                    if (response.data != null) {
                        emit(Resource.Success(response.data.items))
                    } else {
                        emit(Resource.Error(ApiError(ErrorType.FAILURE, "Something went wrong")))
                    }
                }
            }
        }
    }

}