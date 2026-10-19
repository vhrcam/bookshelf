package com.example.bookshelf.data

import android.util.Log
import com.example.bookshelf.model.BookDetails
import com.example.bookshelf.network.BooksApiService

interface BooksRepository {
    suspend fun searchBooks(query: String): List<BookDetails>
}

class NetworkBooksRepository(
    private val booksApiService: BooksApiService
) : BooksRepository {
    override suspend fun searchBooks(query: String): List<BookDetails> {
        Log.d("BooksRepository", "Starting search request")

        val searchResponse = booksApiService.searchBooks(query)
        val items = searchResponse.items ?: emptyList()

        Log.d("BooksRepository", "Search succeeded! Found ${items.size} books")

        return items
    }
}
