package com.anupdey.app.discover_github_repositories.data.repository

import app.cash.turbine.test
import com.anupdey.app.discover_github_repositories.MainDispatcherRule
import com.anupdey.app.discover_github_repositories.data.remote.endpoints.FakeGithubAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class GithubRepositoryImplTest {

    private lateinit var repository: GithubRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        val fakeApi = FakeGithubAPI()
        repository = FakeGithubRepository(fakeApi)
    }

    @Test
    fun fetchTopRepository() = runBlocking {
        val fetchDataCount = 3
        repository.fetchTopRepos("Android", "star", fetchDataCount, 1).test {
            val emission1 = awaitItem()
            val list = emission1.data
            assertThat(list!!.size).isEqualTo(fetchDataCount)
            cancelAndConsumeRemainingEvents()
        }
    }

}