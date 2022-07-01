package com.anupdey.app.discover_github_repositories.utils.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val modifiedRequest = request.newBuilder()
            //.addHeader("Authorization", BuildConfig.GITHUB_API_KEY)
            .build()
        return chain.proceed(modifiedRequest)
    }
}