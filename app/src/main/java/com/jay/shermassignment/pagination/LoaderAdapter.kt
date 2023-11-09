package com.jay.shermassignment.pagination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jay.shermassignment.databinding.PagingLoaderBinding

class LoaderAdapter : LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val binding =
            PagingLoaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoaderViewHolder(binding)
    }
    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class LoaderViewHolder(binding: PagingLoaderBinding) : RecyclerView.ViewHolder(binding.root) {
        val progressbar = binding.pgLoading

        fun bind(loadState: LoadState) {
            progressbar.isVisible=loadState is LoadState.Loading
        }
    }


}