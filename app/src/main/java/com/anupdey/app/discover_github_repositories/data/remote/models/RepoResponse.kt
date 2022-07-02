package com.anupdey.app.discover_github_repositories.data.remote.models


import com.google.gson.annotations.SerializedName

data class RepoResponse(
    @SerializedName("total_count")
    var totalCount: Int = 0,
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean = false,
    @SerializedName("items")
    var items: List<RepoData> = listOf()
)