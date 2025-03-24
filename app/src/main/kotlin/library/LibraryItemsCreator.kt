package library

class LibraryItemsCreator {
    fun createBook(id: Int, isAvailable: Boolean, name: String, pages: Int, author: String): Book {
        return Book(id, isAvailable, name, pages, author)
    }

    fun createNewspaper(id: Int, isAvailable: Boolean, name: String, releaseNumber: Int, releaseMonth: String): Newspaper {
        return Newspaper(id, isAvailable, name, releaseNumber, releaseMonth)
    }

    fun createDisc(id: Int, isAvailable: Boolean, name: String, type: String): Disc {
        return Disc(id, isAvailable, name, type)
    }
}