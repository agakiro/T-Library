package db

import library.Book
import library.Disc
import library.Library
import library.Newspaper

class ItemRepository(private val itemDao: ItemDao, private val sortSettings: SortSettings) {

    private var currentOffset = 0
    private val pageSize = 14


    suspend fun loadAllItems(): List<Library> {
        return itemDao.getAll().map { it.toDomainItem() }
    }

    suspend fun insertItem(items: Library) {
        when(items) {
            is Book -> itemDao.insertItem(items.toUniversalItem())
            is Newspaper -> itemDao.insertItem(items.toUniversalItem())
            is Disc -> itemDao.insertItem(items.toUniversalItem())
        }
    }

    suspend fun loadInitialPage(): List<Library> {
        currentOffset = 0
        val sortType = sortSettings.getSortType()
        val items = when (sortType) {
            SortType.BY_NAME -> itemDao.getAllSortedByName(pageSize, currentOffset)
            SortType.BY_ID -> itemDao.getAllSortedByDate(pageSize, currentOffset)
        }
        return items.map { it.toDomainItem() }
    }

    suspend fun loadNextPage(): List<Library> {
        val sortType = sortSettings.getSortType()
        currentOffset += pageSize / 2
        val items = when (sortType) {
            SortType.BY_NAME -> itemDao.getAllSortedByName(pageSize / 2, currentOffset)
            SortType.BY_ID -> itemDao.getAllSortedByDate(pageSize / 2, currentOffset)
        }
        return items.map { it.toDomainItem() }
    }

    suspend fun loadPreviousPage(): List<Library> {
        val sortType = sortSettings.getSortType()
        currentOffset = (currentOffset - pageSize / 2).coerceAtLeast(0)
        val items = when (sortType) {
            SortType.BY_NAME -> itemDao.getAllSortedByName(pageSize / 2, currentOffset)
            SortType.BY_ID -> itemDao.getAllSortedByDate(pageSize / 2, currentOffset)
        }
        return items.map { it.toDomainItem() }
    }

    fun saveSortType(sortType: SortType) {
        sortSettings.saveSortType(sortType)
    }

    fun getSortType(): SortType {
        return sortSettings.getSortType()
    }
}