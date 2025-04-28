package db

import androidx.room.Entity
import androidx.room.PrimaryKey
import library.Book
import library.Disc
import library.Library
import library.Newspaper


@Entity(tableName = "library_items")
data class ItemEntity(
    @PrimaryKey val id: Int,

    val name: String,
    val isAvailable: Boolean,
    val imageId: Int,
    val itemType: ItemTypesEnum,

    val author: String? = null,
    val pages: Int? = null,

    val releaseNumber: Int? = null,
    val releaseMonth: String? = null,

    val discType: String? = null
)

enum class ItemTypesEnum {
    BOOK,
    NEWSPAPER,
    DISC
}

fun Book.toUniversalItem(): ItemEntity {
    return ItemEntity(
        id = this.id,
        name = this.name,
        isAvailable = this.isAvailable,
        imageId = this.imageId,
        itemType = ItemTypesEnum.BOOK,
        author = this.author,
        pages = this.pages,
        releaseNumber = null,
        releaseMonth = null,
        discType = null
    )
}

fun Newspaper.toUniversalItem(): ItemEntity {
    return ItemEntity(
        id = this.id,
        name = this.name,
        isAvailable = this.isAvailable,
        imageId = this.imageId,
        itemType = ItemTypesEnum.NEWSPAPER,
        releaseNumber = this.releaseNumber,
        releaseMonth = this.releaseMonth,
        author = null,
        pages = null,
        discType = null
    )
}

fun Disc.toUniversalItem(): ItemEntity {
    return ItemEntity(
        id = this.id,
        name = this.name,
        isAvailable = this.isAvailable,
        imageId = this.imageId,
        itemType = ItemTypesEnum.DISC,
        discType = this.type,
        author = null,
        pages = null,
        releaseNumber = null,
        releaseMonth = null
    )
}

fun ItemEntity.toDomainItem(): Library = when(itemType) {
    ItemTypesEnum.BOOK -> Book(
        id = id,
        name = name,
        isAvailable = isAvailable,
        imageId = imageId,
        author = author ?: "",
        pages = pages ?: 0
    )

    ItemTypesEnum.NEWSPAPER -> Newspaper(
        id = id,
        name = name,
        isAvailable = isAvailable,
        imageId = imageId,
        releaseNumber = releaseNumber ?: 0,
        releaseMonth = releaseMonth ?: ""
    )

    ItemTypesEnum.DISC -> Disc(
        id = id,
        name = name,
        isAvailable = isAvailable,
        imageId = imageId,
        type = discType ?: ""
    )
}
