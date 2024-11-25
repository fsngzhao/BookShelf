package com.example.books

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BooksAdapter(
    private val thumbnails: List<String>,
    private val titles: List<String>
) : RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {

    inner class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.bookImageView)
        val titleView: TextView = view.findViewById(R.id.bookTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val thumbnailUrl = thumbnails.getOrNull(position) // 防止越界
        val title = titles.getOrNull(position) ?: "Unknown Title"

        Glide.with(holder.imageView.context)
            .load(thumbnailUrl)
            .placeholder(R.drawable.placeholder_book) // 默认占位图
            .into(holder.imageView)

        holder.titleView.text = title
    }

    override fun getItemCount(): Int {
        return thumbnails.size
    }
}

