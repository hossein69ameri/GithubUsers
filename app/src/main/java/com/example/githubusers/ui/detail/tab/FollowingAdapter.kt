package com.example.githubusers.ui.detail.tab

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.githubusers.data.model.ResponseFollowing.ResponseFollowingItem
import com.example.githubusers.databinding.ItemUserBinding
import javax.inject.Inject

class FollowingAdapter @Inject constructor() : RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {

    private lateinit var binding: ItemUserBinding
    private var FollowingList = emptyList<ResponseFollowingItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //getItem from PagingDataAdapter
        holder.bind(FollowingList[position])
        //Not duplicate items
        holder.setIsRecyclable(false)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() = FollowingList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: ResponseFollowingItem) {
            binding.apply {
                itemUserImage.load(item.avatarUrl)
                itemUserName.text = item.login.toString()
                //click
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((ResponseFollowingItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ResponseFollowingItem) -> Unit) {
        onItemClickListener = listener
    }

    fun setData(data: List<ResponseFollowingItem>) {
        val moviesDiffUtil = NotesDiffUtils(FollowingList, data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtil)
        FollowingList = data
        diffUtils.dispatchUpdatesTo(this)
    }

    class NotesDiffUtils(private val oldItem: List<ResponseFollowingItem>, private val newItem: List<ResponseFollowingItem>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }
    }
}