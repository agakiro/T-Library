package forManaging

import library.Book
import library.Disc
import library.Newspaper

interface Shops<T> {
    fun sell(): T
}

class BookShop: Shops<Book> {
    override fun sell(): Book {
        return Book(74, true, "Хроники Нарнии", 912, "Клайв Стейплз Льюис")
    }
}

class NewspaperShop: Shops<Newspaper> {
    override fun sell(): Newspaper {
        return Newspaper(123, true, "Вечерняя Москва", 42, "Август")
    }
}

class DiscShop: Shops<Disc> {
    override fun sell(): Disc {
        return Disc(32, true, "Mortal Kombat", "CD")
    }
}
