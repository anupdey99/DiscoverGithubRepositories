package com.anupdey.app.discover_github_repositories.data.remote.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepoData(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("full_name")
    var fullName: String? = "",
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("language")
    var language: String? = "",
    @SerializedName("stargazers_count")
    var stargazersCount: Int = 0,
    @SerializedName("watchers_count")
    var watchersCount: Int = 0,
    @SerializedName("forks_count")
    var forksCount: Int = 0,
    @SerializedName("created_at")
    var createdAt: String? = "",
    @SerializedName("updated_at")
    var updatedAt: String? = "",
    @SerializedName("owner")
    var owner: Owner? = Owner()
) : Parcelable