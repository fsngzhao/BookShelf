package com.example.books


import Book
import BookResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleBooksService {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String
    ): BookResponse

    @GET("volumes/{volumeId}")
    suspend fun getBookDetails(
        @Path("volumeId") volumeId: String
    ): Book
}
