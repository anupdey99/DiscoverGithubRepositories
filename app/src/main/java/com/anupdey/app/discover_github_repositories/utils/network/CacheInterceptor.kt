package com.anupdey.app.discover_github_repositories.utils.network

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CacheInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val originalResponse: Response = chain.proceed(request)

        val cacheControl = CacheControl.Builder()
            .maxStale(1, TimeUnit.HOURS)
            .build()

        return originalResponse.newBuilder()
            .removeHeader("Pragma")
            .removeHeader("Cache-Control")
            .header("Cache-Control", cacheControl.toString())
            .build()

    }

}