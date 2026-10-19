package com.example.bookshelf.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshelf.data.BooksRepository
import com.example.bookshelf.data.NetworkBooksRepository
import com.example.bookshelf.network.BooksApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class Book(
    val title: String,
    val thumbnailUrl: String
)

sealed interface BookshelfUiState {
    data class Success(val books: List<Book>) : BookshelfUiState
    data class Error(val message: String) : BookshelfUiState
    object Loading : BookshelfUiState
}

class BookshelfViewModel(
    private val booksRepository: BooksRepository = NetworkBooksRepository(BooksApi.retrofitService)
) : ViewModel() {

    var bookshelfUiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Loading)
        private set

    init {
        getBooks("true crime")
    }

    fun getBooks(query: String) {
        viewModelScope.launch {
            bookshelfUiState = BookshelfUiState.Loading
            bookshelfUiState = try {
                val bookDetailsList = booksRepository.searchBooks(query)
                val books = bookDetailsList.mapNotNull { details ->
                    val thumbnail = details.volumeInfo.imageLinks?.thumbnail
                    if (thumbnail != null) {
                        Book(
                            title = details.volumeInfo.title,
                            thumbnailUrl = thumbnail.replace("http://", "https://")
                        )
                    } else {
                        null
                    }
                }
                BookshelfUiState.Success(books)
            } catch (e: IOException) {
                Log.e("BookshelfViewModel", "IOException while loading books", e)
                BookshelfUiState.Error("Network problem: ${e.javaClass.simpleName}: ${e.message}")
            } catch (e: HttpException) {
                Log.e("BookshelfViewModel", "HttpException while loading books", e)
                BookshelfUiState.Error("HTTP error ${e.code()}")
            } catch (e: Exception) {
                Log.e("BookshelfViewModel", "Unexpected error while loading books", e)
                BookshelfUiState.Error("Unexpected: ${e.javaClass.simpleName}: ${e.message}")
            }
        }
    }
}