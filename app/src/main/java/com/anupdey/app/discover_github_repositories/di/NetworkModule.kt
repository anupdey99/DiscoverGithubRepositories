package com.anupdey.app.discover_github_repositories.di

import com.anupdey.app.discover_github_repositories.data.remote.endpoints.GithubAPI
import com.anupdey.app.discover_github_repositories.utils.AppConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import com.anupdey.app.discover_github_repositories.BuildConfig
import com.anupdey.app.discover_github_repositories.utils.network.AuthInterceptor

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
        return interceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubAPI(client: OkHttpClient): GithubAPI {
        return Retrofit.Builder()
            .baseUrl(AppConstant.BASE_URL_GITHUB)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(GithubAPI::class.java)
    }

}