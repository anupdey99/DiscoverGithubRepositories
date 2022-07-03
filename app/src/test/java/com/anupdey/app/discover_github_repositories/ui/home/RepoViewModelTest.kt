package com.anupdey.app.discover_github_repositories.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anupdey.app.discover_github_repositories.MainCoroutineRule
import com.anupdey.app.discover_github_repositories.data.remote.endpoints.FakeGithubAPI
import com.anupdey.app.discover_github_repositories.data.repository.FakeGithubRepository
import com.google.common.truth.Truth
import app.cash.turbine.test
import com.anupdey.app.discover_github_repositories.ui.top_repo.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RepoViewModelTest {

    private lateinit var viewModel: RepoViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        val fakeApi = FakeGithubAPI()
        val repository = FakeGithubRepository(fakeApi)
        viewModel = RepoViewModel(repository)
    }

    @Test
    fun fetchTopRepository() = runBlocking {
        viewModel.fetchTopRepos()
        viewModel.viewStateEvent.test {
            val emission1 = awaitItem()
            if (emission1 is ViewState.ProgressState) {
                Truth.assertThat("Loading").isEqualTo("Loading")
            }
            val emission2 = awaitItem()
            if (emission2 is ViewState.InitData) {
                Truth.assertThat(emission2.list.size).isEqualTo(5)
            }
            cancelAndConsumeRemainingEvents()
        }
    }

}