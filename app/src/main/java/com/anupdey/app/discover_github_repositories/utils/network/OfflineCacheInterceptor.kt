package com.anupdey.app.discover_github_repositories.utils.network

import android.app.Application
import com.anupdey.app.discover_github_repositories.utils.ext.isNetworkAvailable
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class OfflineCacheInterceptor @Inject constructor(
    private val application: Application
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isNetworkAvailable(application)) {
            val cacheControl = CacheControl.Builder()
                .onlyIfCached()
                .maxStale(1, TimeUnit.HOURS)
                .build()
            val offlineRequest = chain.request().newBuilder()
                .cacheControl(cacheControl)
                .removeHeader("Pragma")
                .build()
            return chain.proceed(offlineRequest)
        }

        return chain.proceed(chain.request())
    }
}