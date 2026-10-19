package com.example.bookshelf.network

import com.example.bookshelf.model.BookDetails
import com.example.bookshelf.model.SearchResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://www.googleapis.com/books/v1/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface BooksApiService {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 5
    ): SearchResponse

    @GET("volumes/{volumeId}")
    suspend fun getBookInfo(@Path("volumeId") volumeId: String): BookDetails
}
object BooksApi {
    val retrofitService: BooksApiService by lazy {
        retrofit.create(BooksApiService::class.java)
    }
}