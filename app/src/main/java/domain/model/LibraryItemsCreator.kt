package domain.model

import com.example.library.R

class LibraryItemsCreator {
    fun createBook(id: Int, isAvailable: Boolean, name: String, pages: Int, author: String): Book {
        return Book(R.drawable.book, id, isAvailable, name, pages, author)
    }

    fun createNewspaper(id: Int, isAvailable: Boolean, name: String, releaseNumber: Int, releaseMonth: String): Newspaper {
        return Newspaper(R.drawable.newspaper, id, isAvailable, name, releaseNumber, releaseMonth)
    }

    fun createDisc(id: Int, isAvailable: Boolean, name: String, type: String): Disc {
        return Disc(R.drawable.disc, id, isAvailable, name, type)
    }
}