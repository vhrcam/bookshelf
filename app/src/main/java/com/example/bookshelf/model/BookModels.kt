package com.example.bookshelf.model

data class SearchResponse(
    val items: List<BookDetails>? = null
)

data class BookIdItem(
    val id: String
)

data class BookDetails(
    val id: String,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String,
    val imageLinks: ImageLinks?
)

data class ImageLinks(
    val thumbnail: String?
)