package com.example.githubusers.ui.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.githubusers.data.database.UserEntity
import com.example.githubusers.databinding.ItemFavoriteBinding
import javax.inject.Inject

class FavoriteAdapter @Inject constructor() : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private lateinit var binding: ItemFavoriteBinding
    private var UsersList = emptyList<UserEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        fun bind(item: UserEntity) {
            binding.apply {
                if (item.location.isNullOrEmpty().not()){
                    itemFavoriteLocation.text = item.location
                    itemFavoriteLocation.visibility = View.VISIBLE
                    iconLocation.visibility = View.VISIBLE
                }else{
                    itemFavoriteLocation.visibility = View.INVISIBLE
                    iconLocation.visibility = View.INVISIBLE
                }

                if (item.bio.isNullOrEmpty().not()){
                    itemFavoriteOrganization.text = item.bio
                    itemFavoriteOrganization.visibility = View.VISIBLE
                    iconOrganization.visibility = View.VISIBLE
                }else{
                    itemFavoriteOrganization.visibility = View.INVISIBLE
                    iconOrganization.visibility = View.INVISIBLE
                }
                itemFavoriteName.text = item.name
                itemNumberFavoriteFollower.text = item.follower
                itemNumberFavoriteFollowing.text = item.following
                itemNumberFavoriteRepository.text = item.repo
                itemFavoriteImage.load(item.image)
                //click
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((UserEntity) -> Unit)? = null

    fun setOnItemClickListener(listener: (UserEntity) -> Unit) {
        onItemClickListener = listener
    }

    fun setData(data: List<UserEntity>) {
        val moviesDiffUtil = NotesDiffUtils(UsersList, data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtil)
        UsersList = data
        diffUtils.dispatchUpdatesTo(this)
    }

    class NotesDiffUtils(private val oldItem: List<UserEntity>, private val newItem: List<UserEntity>) : DiffUtil.Callback() {

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