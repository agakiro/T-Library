package extraFunction

import com.example.library.R
import library.Book
import library.Disc
import library.Library
import library.Newspaper

class Digitizer{
    fun <T : Library> digitize(item: T): Disc {
        return when(item) {
            is Book -> Disc(R.drawable.disc, item.id, true, item.name, "CD")
            is Newspaper -> Disc(R.drawable.disc, item.id, true, item.name, "CD")
            else -> throw error("Ошибка при поптыке оцифровки")
        }
    }
}