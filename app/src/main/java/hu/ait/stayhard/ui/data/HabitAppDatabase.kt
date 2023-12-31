package hu.ait.stayhard.ui.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HabitItem::class], version = 3, exportSchema = false)
abstract class HabitAppDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDAO

    companion object {
        @Volatile
        private var Instance: HabitAppDatabase? = null

        fun getDatabase(context: Context): HabitAppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, HabitAppDatabase::class.java,
                    "habit_database.db")
                    // Setting this option in your app's database builder means that Room
                    // permanently deletes all data from the tables in your database when it
                    // attempts to perform a migration with no defined migration path.
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}