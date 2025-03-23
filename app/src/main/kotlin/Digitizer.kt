class Digitizer{
    fun <T : Library> digitize(item: T): Disc {
        return when(item) {
            is Book -> Disc(item.id, true, item.name, "CD")
            is Newspaper -> Disc(item.id, true, item.name, "CD")
            else -> throw error("Ошибка при поптыке оцифровки")
        }
    }
}