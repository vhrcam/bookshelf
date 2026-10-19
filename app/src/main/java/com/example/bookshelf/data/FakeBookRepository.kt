package com.example.bookshelf.data

import com.example.bookshelf.model.BookDetails
import com.example.bookshelf.model.ImageLinks
import com.example.bookshelf.model.VolumeInfo

class FakeBooksRepository : BooksRepository {
    override suspend fun searchBooks(query: String): List<BookDetails> {
        return listOf(
            BookDetails(
                id = "fake1",
                volumeInfo = VolumeInfo(
                    title = "Fake True Crime Book",
                    imageLinks = ImageLinks(thumbnail = "https://example.com/fake.jpg")
                )
            )
        )
    }
}