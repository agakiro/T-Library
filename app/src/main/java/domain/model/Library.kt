package domain.model

import androidx.room.Entity

@Entity(tableName = "library_items")
abstract class Library(open val id: Int, open var isAvailable: Boolean, open val name: String, open val imageId: Int): Returnable,
    Readable, Takeable {
    abstract  fun getDetailedInformation(): String

    fun getSummaryInformation(): String {
        return "$name доступна: ${if (isAvailable) "Да" else "Нет" }"
    }
}
