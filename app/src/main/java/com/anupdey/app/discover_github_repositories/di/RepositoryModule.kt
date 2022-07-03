package com.anupdey.app.discover_github_repositories.di

import com.anupdey.app.discover_github_repositories.data.repository.GithubRepository
import com.anupdey.app.discover_github_repositories.data.repository.GithubRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUnsplashRepository(
        repository: GithubRepositoryImpl
    ): GithubRepository

}