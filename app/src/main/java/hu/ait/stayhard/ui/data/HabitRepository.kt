package hu.ait.stayhard.ui.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HabitRepository @Inject constructor(
    private val habitDAO: HabitDAO
) {

    fun getAllHabitList(): Flow<List<HabitItem>> {
        return habitDAO.getAllHabits()
    }

    suspend fun getAllHabitNum(): Int {
        return habitDAO.getHabitsNum()
    }

    suspend fun getBoughtHabitNum(): Int {
        return habitDAO.getBoughtHabitsNum()
    }

    suspend fun addHabitItem(habitItem: HabitItem) {
        habitDAO.insert(habitItem)
    }

    suspend fun removeHabitItem(habitItem: HabitItem) {
        habitDAO.delete(habitItem)
    }

    suspend fun editHabitItem(editedHabit: HabitItem) {
        habitDAO.update(editedHabit)
    }

    suspend fun changeHabitState(habitItem: HabitItem, status: Status) {
        val newHabitItem = habitItem.copy(status = status)
        habitDAO.update(newHabitItem)
    }

    suspend fun clearAllHabits() {
        habitDAO.deleteAllHabits()
    }

    fun getHabitListSortedByCategory(category: Category): Flow<List<HabitItem>> {
        return habitDAO.getHabitListSortedByCategory(category)
    }
}
