package domain.repository

import data.local.SortType
import domain.model.Library

interface ItemRepository {
    suspend fun loadAllItems(): List<Library>

    suspend fun insertItem(items: Library)

    suspend fun loadInitialPage(): List<Library>

    suspend fun loadNextPage(): List<Library>

    suspend fun loadPreviousPage(): List<Library>

    fun saveSortType(sortType: SortType)

    fun getSortType(): SortType
}