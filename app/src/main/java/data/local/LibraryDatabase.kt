package data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [ItemEntity::class],
    version = 6,
    exportSchema = false
)
@TypeConverters(DatabaseConverter::class)
abstract class LibraryDatabase : RoomDatabase() {

    abstract fun getDao(): ItemDao

    companion object{
        @Volatile
        private var INSTANCE: LibraryDatabase? = null

        fun getDb(context: Context): LibraryDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LibraryDatabase::class.java,
                    "test.db"
                )
                    .fallbackToDestructiveMigration(false)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}