package db

import android.content.Context

class SortSettings(context: Context) {
    private val prefs = context.getSharedPreferences("library_prefs", Context.MODE_PRIVATE)

    companion object{
        private const val KEY_SORT_TYPE = "sort_type"
    }

    fun saveSortType(type: SortType) {
        prefs.edit().putString(KEY_SORT_TYPE, type.name).apply()
    }

    fun getSortType(): SortType {
        val typeName = prefs.getString(KEY_SORT_TYPE, SortType.BY_ID.name) ?: SortType.BY_ID.name
        return SortType.valueOf(typeName)
    }
}

enum class SortType {
    BY_NAME,
    BY_ID
}