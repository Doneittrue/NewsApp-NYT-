package com.andrew.newsapp.presentation.features.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andrew.newsapp.R
import com.andrew.newsapp.databinding.StoryListItemBinding
import com.andrew.newsapp.entities.DbNewsPiece

private val diffUtilCallbacks by lazy {
    object : DiffUtil.ItemCallback<DbNewsPiece>() {
        override fun areItemsTheSame(oldItem: DbNewsPiece, newItem: DbNewsPiece) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: DbNewsPiece, newItem: DbNewsPiece) =
            oldItem == newItem
    }

}

class TopStoriesViewHolder(
    val binding: StoryListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(story: DbNewsPiece) {
        binding.story = story
        binding.favouriteImageView.setImageResource(handleFavourite(!story.isFavourite))
    }

}

fun handleFavourite(isFavourite: Boolean) =
    if (isFavourite) R.drawable.not_favorite else R.drawable.favorite

class TopStoriesAdapter(
    private val onFavouriteClick: (DbNewsPiece) -> Unit
) : PagedListAdapter<DbNewsPiece, TopStoriesViewHolder>(diffUtilCallbacks) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = DataBindingUtil.inflate<StoryListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.story_list_item,
            parent,
            false
        )
        .let { TopStoriesViewHolder(it) }

    override fun onBindViewHolder(holder: TopStoriesViewHolder, position: Int) = getItem(position)
        ?.also { item ->
            holder.binding.favouriteImageView.run {
                setOnClickListener { onFavouriteClick(item);setImageResource(handleFavourite(item.isFavourite)) }
            }
        }
        ?.let { holder.bind(it) } ?: Unit
}
