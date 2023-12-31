package hu.ait.stayhard.ui.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDAO {
    @Query("SELECT * from habittable")
    fun getAllHabits(): Flow<List<HabitItem>>

    @Query("SELECT * from habittable WHERE id = :id")
    fun getHabits(id: Int): Flow<HabitItem>

    @Query("SELECT COUNT(*) from habittable")
    suspend fun getHabitsNum(): Int

    @Query("SELECT COUNT(*) FROM habittable WHERE status = 'NOT_ACCOMPLISHED'")
    suspend fun getBoughtHabitsNum(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(habit: HabitItem)

    @Update
    suspend fun update(habit: HabitItem)

    @Delete
    suspend fun delete(habit: HabitItem)

    @Query("DELETE from habittable")
    suspend fun deleteAllHabits()

    @Query("SELECT * FROM habittable WHERE category = :category ORDER BY name ASC")
    fun getHabitListSortedByCategory(category: Category): Flow<List<HabitItem>>
}