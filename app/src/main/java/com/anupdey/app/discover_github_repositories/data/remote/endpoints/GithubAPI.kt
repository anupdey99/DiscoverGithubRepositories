package com.anupdey.app.discover_github_repositories.data.remote.endpoints

import com.anupdey.app.discover_github_repositories.data.remote.models.RepoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubAPI {

    @GET("search/repositories")
    suspend fun fetchTopRepos(
        @Query("q") searchKey: String,
        @Query("sort") sort: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): RepoResponse

}