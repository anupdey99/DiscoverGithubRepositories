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
        val holder = BaseViewHolder(initializeViewBinding(layoutInflater, parent, viewType))
        holder.binding.root.setOnClickListener {
            if (holder.absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                listener?.invoke(
                    differ.currentList[holder.absoluteAdapterPosition],
                    holder.absoluteAdapterPosition,
                    false
                )
            }
        }
        holder.binding.root.setOnLongClickListener {
            if (holder.absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                listener?.invoke(
                    differ.currentList[holder.absoluteAdapterPosition],
                    holder.absoluteAdapterPosition,
                    true
                )
            }
            return@setOnLongClickListener true
        }
        return holder
    }

    override fun getItemCount() = differ.currentList.size

    class BaseViewHolder<VH : ViewBinding>(val binding: VH) : RecyclerView.ViewHolder(binding.root)

    private var listener: ((item: T, position: Int, isLong: Boolean) -> Unit)? = null

    fun setOnClickLister(listener: (item: T, position: Int, isLong: Boolean) -> Unit) {
        this.listener = listener
    }
}