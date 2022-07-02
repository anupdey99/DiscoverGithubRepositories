package com.anupdey.app.discover_github_repositories.data.remote.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("login")
    var login: String? = "",
    @SerializedName("avatar_url")
    var avatarUrl: String? = ""
) : Parcelable