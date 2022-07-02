package com.anupdey.app.discover_github_repositories.ui.top_repo

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.anupdey.app.discover_github_repositories.R
import com.anupdey.app.discover_github_repositories.databinding.FragmentTopRepositoryBinding
import com.anupdey.app.discover_github_repositories.ui.home.RepoViewModel
import com.anupdey.app.discover_github_repositories.utils.ext.hideError
import com.anupdey.app.discover_github_repositories.utils.ext.showError
import com.anupdey.app.discover_github_repositories.utils.ext.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TopRepositoryFragment: Fragment() {

    private var _binding: FragmentTopRepositoryBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: RepoViewModel by activityViewModels()

    @Inject
    lateinit var dataAdapter: TopRepositoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentTopRepositoryBinding.inflate(inflater, container, false).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        clickListeners()
        observerViewState()
        menuProvider()
        viewModel.fetchTopRepos()
    }

    private fun initView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = dataAdapter
        }
    }

    private fun clickListeners() {
        dataAdapter.setOnClickLister { item, _ ->

        }
    }

    private fun observerViewState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewStateEvent.collect { state ->
                    when (state) {
                        is ViewState.ProgressState -> {
                            binding.progressBar.isVisible = state.isShow
                        }
                        is ViewState.EmptyState -> {
                            binding.emptyView.isVisible = state.isShow
                        }
                        is ViewState.InitData -> {
                            dataAdapter.differ.submitList(state.list.toList())
                            hideError(binding.errorView)
                            binding.recyclerView.postDelayed({
                                binding.recyclerView.smoothScrollToPosition(0)
                            }, 200L)
                        }
                        is ViewState.ShowError -> {
                            context?.toast(state.error.message)
                            showError(binding.errorView, state.error) {
                                viewModel.retry()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun menuProvider() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_sort, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.actionSortByStar -> {
                        viewModel.sortByStar()
                        true
                    }
                    R.id.actionSortByUpdate -> {
                        viewModel.sortByUpdateDate()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}