package com.example.books

import Book

class BooksRepository(private val api: GoogleBooksService) {
    suspend fun searchBooks(query: String): List<Book> {
        return api.searchBooks(query).items
    }

    suspend fun getBookDetails(volumeId: String): Book {
        return api.getBookDetails(volumeId)
    }
}
