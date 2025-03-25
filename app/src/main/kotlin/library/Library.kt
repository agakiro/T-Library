package library

abstract class Library(val id: Int, var isAvailable: Boolean, val name: String): Returnable,
    Readable, Takeable {
    abstract  fun getDetailedInformation()

    fun getSummaryInformation(): String {
        return "$name доступна: ${if (isAvailable) "Да" else "Нет" }"
    }
}
