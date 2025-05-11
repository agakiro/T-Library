package data.repository

import data.local.ItemDao
import data.local.toDomainItem
import data.local.toUniversalItem
import data.local.SortSettings
import data.local.SortType
import domain.repository.ItemRepository
import domain.model.Book
import domain.model.Disc
import domain.model.Library
import domain.model.Newspaper

class ItemRepositoryImpl(private val itemDao: ItemDao, private val sortSettings: SortSettings) :
    ItemRepository {

    private var currentOffset = 0
    private val pageSize = 14


    override suspend fun loadAllItems(): List<Library> {
        return itemDao.getAll().map { it.toDomainItem() }
    }

    override suspend fun insertItem(items: Library) {
        when(items) {
            is Book -> itemDao.insertItem(items.toUniversalItem())
            is Newspaper -> itemDao.insertItem(items.toUniversalItem())
            is Disc -> itemDao.insertItem(items.toUniversalItem())
        }
    }

    override suspend fun loadInitialPage(): List<Library> {
        currentOffset = 0
        val sortType = sortSettings.getSortType()
        val items = when (sortType) {
            SortType.BY_NAME -> itemDao.getAllSortedByName(pageSize, currentOffset)
            SortType.BY_ID -> itemDao.getAllSortedByDate(pageSize, currentOffset)
        }
        return items.map { it.toDomainItem() }
    }

    override suspend fun loadNextPage(): List<Library> {
        val sortType = sortSettings.getSortType()
        currentOffset += pageSize / 2
        val items = when (sortType) {
            SortType.BY_NAME -> itemDao.getAllSortedByName(pageSize / 2, currentOffset)
            SortType.BY_ID -> itemDao.getAllSortedByDate(pageSize / 2, currentOffset)
        }
        return items.map { it.toDomainItem() }
    }

    override suspend fun loadPreviousPage(): List<Library> {
        val sortType = sortSettings.getSortType()
        currentOffset = (currentOffset - pageSize / 2).coerceAtLeast(0)
        val items = when (sortType) {
            SortType.BY_NAME -> itemDao.getAllSortedByName(pageSize / 2, currentOffset)
            SortType.BY_ID -> itemDao.getAllSortedByDate(pageSize / 2, currentOffset)
        }
        return items.map { it.toDomainItem() }
    }

    override fun saveSortType(sortType: SortType) {
        sortSettings.saveSortType(sortType)
    }

    override fun getSortType(): SortType {
        return sortSettings.getSortType()
    }
}