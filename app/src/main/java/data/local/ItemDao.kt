package data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(items: ItemEntity)

    @Update
    suspend fun update(items: ItemEntity)

    @Query("SELECT * FROM library_items")
    suspend fun getAll(): List<ItemEntity>

    @Query("SELECT * FROM library_items ORDER BY name ASC LIMIT :limit OFFSET :offset")
    suspend fun getAllSortedByName(limit: Int, offset: Int): List<ItemEntity>

    @Query("SELECT * FROM library_items ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getAllSortedByDate(limit: Int, offset: Int): List<ItemEntity>
}