package com.example.books

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val repository = BooksRepository(RetrofitInstance.api)
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val thumbnails = mutableListOf<String>()
        val titles = mutableListOf<String>()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = BooksAdapter(thumbnails, titles)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val bookList = repository.searchBooks("jazz history") ?: listOf()
                for (book in bookList) {
                    val bookDetails = try {
                        repository.getBookDetails(book.id)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                    }

                    val thumbnailUrl = bookDetails?.volumeInfo?.imageLinks?.thumbnail
                        ?.replace(Regex("^http://"), "https://")
                    val title = bookDetails?.volumeInfo?.title ?: "Unknown Title"

                    if (thumbnailUrl != null) {
                        thumbnails.add(thumbnailUrl)
                        titles.add(title)
                    }
                }

                withContext(Dispatchers.Main) {
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}



