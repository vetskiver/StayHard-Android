package hu.ait.stayhard.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.ait.stayhard.ui.data.Category
import hu.ait.stayhard.ui.data.HabitDAO
import hu.ait.stayhard.ui.data.HabitItem
import hu.ait.stayhard.ui.data.HabitRepository
import hu.ait.stayhard.ui.data.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(
    private val habitDAO: HabitDAO,
    private val habitRepository: HabitRepository
) : ViewModel() {

    fun getAllHabitList(): Flow<List<HabitItem>> {
        return habitDAO.getAllHabits()
    }

    suspend fun getNotAccomplishedHabitNum(): Int {
        return habitDAO.getHabitsNum()
    }

    suspend fun getAccomplishedHabitNum(): Int {
        return habitDAO.getBoughtHabitsNum()
    }

    fun addHabitList(habitItem: HabitItem) {
        viewModelScope.launch {
            habitDAO.insert(habitItem)
        }
    }

    fun removeHabitItem(habitItem: HabitItem) {
        viewModelScope.launch {
            habitDAO.delete(habitItem)
        }
    }

    fun changeHabitState(habitItem: HabitItem, status: Status) {
        val newHabitItem = habitItem.copy(status = status)
        viewModelScope.launch {
            habitDAO.update(newHabitItem)
        }
    }

    fun clearAllHabits() {
        viewModelScope.launch {
            habitDAO.deleteAllHabits()
        }
    }

    fun getHabitListSortedByCategory(category: Category): Flow<List<HabitItem>> {
        return habitRepository.getHabitListSortedByCategory(category)
    }
}