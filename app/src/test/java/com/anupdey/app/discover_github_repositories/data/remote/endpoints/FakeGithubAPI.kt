package com.anupdey.app.discover_github_repositories.data.remote.endpoints

import com.anupdey.app.discover_github_repositories.data.remote.models.Owner
import com.anupdey.app.discover_github_repositories.data.remote.models.RepoData
import com.anupdey.app.discover_github_repositories.data.remote.models.RepoResponse
import java.io.IOException

class FakeGithubAPI : GithubAPI {

    var isError: Boolean = false
    var isEmpty: Boolean = false
    var failureMsg: String = "Throw test failure"

    override suspend fun fetchTopRepos(searchKey: String, sort: String, perPage: Int, page: Int): RepoResponse {
        if (isError) {
            throw IOException(failureMsg)
        }
        if (isEmpty) {
            return RepoResponse()
        }
        return RepoResponse(
            perPage,
            false,
            List(perPage) {
                generateRepoData(it + 1)
            }
        )
    }

    private fun generateRepoData(index: Int): RepoData {
        return RepoData(
            index,
            "repoUser${index}",
            "repoUser${index}/repo${index}",
            "description",
            "Kotlin",
            100,
            100,
            100,
            "2020-07-01T07:34:11Z",
            "2022-07-01T07:34:11Z",
            Owner(
                index,
                "repoUser${index}",
                "https://avatars.githubusercontent.com/u/14101776?v=4"
            )

        )
    }
}