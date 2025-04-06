package forManaging

import com.example.library.R
import library.Book
import library.Disc
import library.Newspaper

interface Shops<T> {
    fun sell(): T
}

class BookShop: Shops<Book> {
    override fun sell(): Book {
        return Book(R.drawable.book, 74, true, "Хроники Нарнии", 912, "Клайв Стейплз Льюис")
    }
}

class NewspaperShop: Shops<Newspaper> {
    override fun sell(): Newspaper {
        return Newspaper(R.drawable.newspaper, 123, true, "Вечерняя Москва", 42, "Август")
    }
}

class DiscShop: Shops<Disc> {
    override fun sell(): Disc {
        return Disc(R.drawable.disc, 32, true, "Mortal Kombat", "CD")
    }
}
