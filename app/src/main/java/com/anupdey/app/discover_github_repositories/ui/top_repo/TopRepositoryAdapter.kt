package com.anupdey.app.discover_github_repositories.ui.top_repo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.anupdey.app.discover_github_repositories.data.remote.models.RepoData
import com.anupdey.app.discover_github_repositories.databinding.ItemViewRepositoryBinding
import com.anupdey.app.discover_github_repositories.ui.base.BaseRecyclerAdapter
import com.anupdey.app.discover_github_repositories.utils.ext.convertStarCount
import com.anupdey.app.discover_github_repositories.utils.ext.formatDate
import javax.inject.Inject

class TopRepositoryAdapter @Inject constructor(

): BaseRecyclerAdapter<RepoData, ItemViewRepositoryBinding>() {


    override fun initializeViewBinding(layoutInflater: LayoutInflater, parent: ViewGroup, viewType: Int): ItemViewRepositoryBinding {
        val binding = ItemViewRepositoryBinding.inflate(layoutInflater, parent, false)
        binding.root.setOnClickListener {

        }
        return binding
    }

    override fun initializeDiffItemCallback(): DiffUtil.ItemCallback<RepoData> {
        return object : DiffUtil.ItemCallback<RepoData>() {
            override fun areItemsTheSame(oldItem: RepoData, newItem: RepoData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RepoData, newItem: RepoData): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ItemViewRepositoryBinding>, position: Int) {
        val model = differ.currentList[position]
        val binding = holder.binding
        binding.apply {
            title.text = model.fullName
            description.text = model.description
            starCount.text = convertStarCount(model.stargazersCount)
            language.text = model.language ?: "-"
            updated.text = "Updated on ${formatDate(model.updatedAt)}"
        }
    }

}