package data.local

import androidx.room.TypeConverter

class DatabaseConverter {
    @TypeConverter
    fun fromItemType(type: ItemTypesEnum): String = type.name

    @TypeConverter
    fun toItemType(value: String): ItemTypesEnum = ItemTypesEnum.valueOf(value)
}