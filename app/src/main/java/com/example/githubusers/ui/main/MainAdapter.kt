package com.example.githubusers.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.githubusers.data.model.ResponseUsers.Item
import com.example.githubusers.databinding.ItemUserBinding
import javax.inject.Inject

class MainAdapter @Inject constructor() : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private lateinit var binding: ItemUserBinding
    private var UsersList = emptyList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //getItem from PagingDataAdapter
        holder.bind(UsersList[position])
        //Not duplicate items
        holder.setIsRecyclable(false)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() = UsersList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Item) {
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

    private var onItemClickListener: ((Item) -> Unit)? = null

    fun setOnItemClickListener(listener: (Item) -> Unit) {
        onItemClickListener = listener
    }

    fun setData(data: List<Item>) {
        val moviesDiffUtil = NotesDiffUtils(UsersList, data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtil)
        UsersList = data
        diffUtils.dispatchUpdatesTo(this)
    }

    class NotesDiffUtils(private val oldItem: List<Item>, private val newItem: List<Item>) : DiffUtil.Callback() {

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