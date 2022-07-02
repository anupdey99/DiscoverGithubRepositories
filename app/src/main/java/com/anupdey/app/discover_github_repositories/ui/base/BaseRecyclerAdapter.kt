package com.anupdey.app.discover_github_repositories.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerAdapter<T : Any, VB : ViewBinding> : RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder<VB>>() {

    val differ by lazy {
        AsyncListDiffer(this, initializeDiffItemCallback())
    }

    protected abstract fun initializeViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): VB

    protected abstract fun initializeDiffItemCallback(): DiffUtil.ItemCallback<T>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val layoutInflater = LayoutInflater.from(parent.context)
        return BaseViewHolder(initializeViewBinding(layoutInflater, parent, viewType))
    }

    override fun getItemCount() = differ.currentList.size

    class BaseViewHolder<VH : ViewBinding>(val binding: VH) : RecyclerView.ViewHolder(binding.root)

    private var listener: ((item: T, isLong: Boolean) -> Unit)? = null

    fun setOnClickLister(listener: (item: T, isLong: Boolean) -> Unit) {
        this.listener = listener
    }
}