package com.anupdey.app.discover_github_repositories.repository

import com.anupdey.app.discover_github_repositories.data.remote.endpoints.GithubAPI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepositoryImpl @Inject constructor(
    private val api: GithubAPI
): GithubRepository {

}