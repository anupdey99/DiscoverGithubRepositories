package com.anupdey.app.discover_github_repositories.utils.network

sealed class Resource<T>(
    val data: T? = null,
    val error: ApiError? = null
) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(error: ApiError, data: T? = null) : Resource<T>(data, error)
}